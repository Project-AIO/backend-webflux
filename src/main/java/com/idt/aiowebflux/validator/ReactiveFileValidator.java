package com.idt.aiowebflux.validator;

import com.idt.aiowebflux.config.UploadProps;
import com.idt.aiowebflux.entity.constant.Extension;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import reactor.core.publisher.Mono;
import xyz.capybara.clamav.ClamavClient;
import xyz.capybara.clamav.commands.scan.result.ScanResult;

@Component
public class ReactiveFileValidator {

    private final Set<String> allowedExt;
    private final Set<String> allowedTypes;
    private DataSize maxBytes;
    private final Tika tika = new Tika();
    private final ClamavClient clam; // blocking

    public ReactiveFileValidator(
            ClamavClient clam,
            @Value("${spring.servlet.multipart.max-file-size}") final DataSize maxSize
    ) {
        this.allowedExt = Extension.getExtensions();
        this.allowedTypes = Extension.getTypes();
        this.clam = clam;
        this.maxBytes = maxSize;
    }

    /**
     * 저장된 파일(Path) 기준 딥 검증.
     * - declaredSize: 클라이언트/메타가 신고한 크기 (로그/경고용)
     * - 실제 크기: Files.size(filePath) 로 재확인
     */
    public Mono<Void> validate(Path filePath, String filename, long declaredSize) {
        return Mono.fromCallable(() -> {
            doValidateBlocking(filePath, filename, declaredSize);
            return (Void) null;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private void doValidateBlocking(Path filePath, String filename, long declaredSize) {
        if (filename == null || filename.isBlank()) {
            throw DomainExceptionCode.FILE_NAME_IS_NULL.newInstance("파일 이름이 비어 있음");
        }

        // --- 1) 실제 크기 확인
        long actualSize;
        try {
            actualSize = Files.size(filePath);
        } catch (IOException e) {
            throw DomainExceptionCode.FILE_READ_FAILED.newInstance(filename + ": 파일 크기 확인 실패: " + e.getMessage());
        }

        // 1-a) 최대 크기 체크
        if (actualSize > maxBytes.toBytes()) {
            throw DomainExceptionCode.FILE_SIZE_EXCEEDED.newInstance(filename + " 파일 크기(" + actualSize + "B)가 허용치(" + maxBytes + "B) 초과");
        }

        // 1-b) 신고 크기와 차이 로그
        if (declaredSize > 0 && declaredSize != actualSize) {
            // 정책에 따라 오류 또는 경고
            // throw DomainExceptionCode.FILE_SIZE_MISMATCH.newInstance(...)
        }

        // --- 2) 확장자 화이트리스트
        String ext = FilenameUtils.getExtension(filename).toLowerCase();
        if (!allowedExt.contains(ext)) {
            throw DomainExceptionCode.FILE_EXTENSION_INVALID.newInstance(filename + " 허용되지 않는 확장자: " + ext);
        }

        // --- 3) MIME 감지 (Tika) - Path 기반 감지 권장
        String detected;
        try {
            detected = tika.detect(filePath); // 스트림 열지 않아도 됨 (헤더 검사 + 일부 sniff)
        } catch (Exception e) {
            throw DomainExceptionCode.FILE_CONTENT_TYPE_INVALID.newInstance(filename + " MIME 감지 실패: " + e.getMessage());
        }
        if (!allowedTypes.contains(detected)) {
            throw DomainExceptionCode.FILE_CONTENT_TYPE_INVALID.newInstance(filename + " 허용되지 않는 MIME: " + detected);
        }

        // --- 4) ZIP 등 차단
        if ("application/zip".equals(detected)) {
            throw DomainExceptionCode.ZIP_FILE_NOT_SUPPORTED.newInstance(filename + " ZIP 업로드 불가");
        }

        // --- 5) ClamAV 바이러스 스캔
        try (InputStream is = Files.newInputStream(filePath)) {
            ScanResult result = clam.scan(is);
            if (result instanceof ScanResult.VirusFound vf) {
                throw DomainExceptionCode.FILE_VIRUS_DETECTED.newInstance(
                        filename + " 바이러스 감지: " + vf.getFoundViruses().keySet());
            }
        } catch (IOException io) {
            throw DomainExceptionCode.FILE_READ_FAILED.newInstance(filename + " 바이러스 스캔 중 파일 읽기 실패: " + io.getMessage());
        }
    }
}
