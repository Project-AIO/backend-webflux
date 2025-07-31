package com.idt.aiowebflux.validator;

import com.idt.aiowebflux.entity.constant.Extension;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.service.ResumableFileStorageService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
public class ReactiveFileValidator {

    private final Set<String> allowedExt;
    private final Set<String> allowedTypes;
    private final ResumableFileStorageService resumableFileStorageService;
    private DataSize maxBytes;
    private final Tika tika = new Tika();

    public ReactiveFileValidator(
            @Value("${spring.servlet.multipart.max-file-size}") final DataSize maxSize,
            ResumableFileStorageService resumableFileStorageService
    ) {
        this.allowedExt = Extension.getExtensions();
        this.allowedTypes = Extension.getTypes();
        this.maxBytes = maxSize;
        this.resumableFileStorageService = resumableFileStorageService;
    }

    /**
     * 저장된 파일(Path) 기준 딥 검증. - declaredSize: 클라이언트/메타가 신고한 크기 (로그/경고용) - 실제 크기: Files.size(filePath) 로 재확인
     */
    public Mono<Void> validate(String filename, long declaredSize, String uploadId, Path path) {
        return Mono.fromCallable(() -> {
                    doValidateBlocking(filename, declaredSize, path);
                    return true;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> {
                    try {
                        Files.deleteIfExists(path);
                        resumableFileStorageService.deleteTempFolder(uploadId);
                        log.warn("검증 실패로 파일 삭제: {}", path);
                    } catch (IOException io) {
                        log.error("검증 실패 후 파일 삭제 실패: {}", io.getMessage());
                        throw new RuntimeException("파일 검증 실패: " + e.getMessage(), e);
                    }
                })
                .then();

    }

    private void doValidateBlocking(String filename, long declaredSize, Path path) {
        if (filename == null || filename.isBlank()) {
            throw DomainExceptionCode.FILE_NAME_IS_NULL.newInstance("파일 이름이 비어 있음");
        }

        // 실제 크기 확인
        long actualSize;
        try {
            actualSize = Files.size(path);
        } catch (IOException e) {
            throw DomainExceptionCode.FILE_READ_FAILED.newInstance(filename + ": 파일 크기 확인 실패: " + e.getMessage());
        }

        // 최대 크기 체크
        if (actualSize > maxBytes.toBytes()) {
            throw DomainExceptionCode.FILE_SIZE_EXCEEDED.newInstance(
                    filename + " 파일 크기(" + actualSize + "B)가 허용치(" + maxBytes + "B) 초과");
        }

        // 신고 크기와 차이 로그
        if (declaredSize > 0 && declaredSize != actualSize) {
            throw DomainExceptionCode.FILE_SIZE_MISMATCH.newInstance(
                    filename + " 신고 크기(" + declaredSize + "B)와 실제 크기(" + actualSize + "B) 불일치");
        }

        // 확장자 화이트리스트
        String ext = FilenameUtils.getExtension(filename).toLowerCase();
        if (!allowedExt.contains(ext)) {
            throw DomainExceptionCode.FILE_EXTENSION_INVALID.newInstance(filename + " 허용되지 않는 확장자: " + ext);
        }

        // MIME 감지 (Tika) - Path 기반 감지 권장
        String detected;
        try {
            detected = tika.detect(path); // 스트림 열지 않아도 됨 (헤더 검사 + 일부 sniff)
        } catch (Exception e) {
            throw DomainExceptionCode.FILE_CONTENT_TYPE_INVALID.newInstance(
                    filename + " MIME 감지 실패: " + e.getMessage());
        }
        if (!allowedTypes.contains(detected)) {
            throw DomainExceptionCode.FILE_CONTENT_TYPE_INVALID.newInstance(filename + " 허용되지 않는 MIME: " + detected);
        }

        // ZIP 등 차단
        if ("application/zip".equals(detected)) {
            throw DomainExceptionCode.ZIP_FILE_NOT_SUPPORTED.newInstance(filename + " ZIP 업로드 불가");
        }
    }
}
