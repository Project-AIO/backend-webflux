package com.idt.aiowebflux.service;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResumableFileStorageService {

    public static final String DOC_ROOT = File.separator + "aio";
    public static final String ROOT_PATH = System.getProperty("user.dir");
    public static final String UPLOAD_PATH = ROOT_PATH + DOC_ROOT;

    /**
     * aio/ (완성본이 모일 루트)
     */
    private final Path root = Path.of(UPLOAD_PATH).normalize();

    /* ---------- 세션 temp 디렉터리 ---------- */
    private Path ensureSessionDir(String uploadId) throws IOException {
        Path p = root.resolve(uploadId);   // temp 만 세션 하위
        Files.createDirectories(p);
        return p;
    }

    private Path tempFilePath(String uploadId, String serverFileId) throws IOException {
        return ensureSessionDir(uploadId).resolve(serverFileId + ".part");
    }

    public Path deleteTempFolder(String uploadId) throws IOException {
        Path dir = root.resolve(uploadId);

        if (Files.isDirectory(dir)) {

            try (Stream<Path> paths = Files.walk(dir)) {
                paths.sorted(Comparator.reverseOrder())   // 파일 → 디렉터리 순으로 삭제
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException e) {
                                // 필요하면 로그만 남기고 계속 진행
                                log.warn("삭제 실패: {}", path, e);
                            }
                        });
            }
            log.debug("removed temp dir {}", dir);
        }

        return dir;   // 원래 선언한 반환형 유지
    }

    // 완성본 경로 (root 바로)
    private Path finalFilePath(String filename) {
        return root.resolve(filename).normalize();   // 이름 겹침 검사 X
    }

    // CHUNK WRITE
    public Mono<Long> writeChunk(String uploadId,
                                 String serverFileId,
                                 long offset,
                                 DataBuffer content,
                                 boolean finalChunk,
                                 long expectedTotalBytes) {

        return Mono.fromCallable(() -> {
                    Path path = tempFilePath(uploadId, serverFileId);
                    try (FileChannel ch = FileChannel.open(
                            path,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.WRITE,
                            StandardOpenOption.READ)) {

                        if (ch.position() != offset) {
                            ch.position(offset);
                        }
                        ByteBuffer nioBuf = content.asByteBuffer();
                        int written = ch.write(nioBuf);

                        if (finalChunk) {
                            ch.force(true);
                            long sz = ch.size();
                            if (expectedTotalBytes > 0 && sz != expectedTotalBytes) {
                                log.warn("size mismatch: {} != {}", sz, expectedTotalBytes);
                            }
                        }
                        return (long) written;
                    }
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doFinally(sig -> DataBufferUtils.release(content));
    }

    // 크기 조회
    public Mono<Long> size(String uploadId, String serverFileId) {
        return Mono.fromCallable(() -> {
            Path p = tempFilePath(uploadId, serverFileId);
            return Files.exists(p) ? Files.size(p) : 0L;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // temp → 루트 이동
    public Mono<Path> finalizeFile(String uploadId,
                                   String serverFileId,
                                   String filename) {

        return Mono.fromCallable(() -> {
            Path src = tempFilePath(uploadId, serverFileId);
            Path dst = finalFilePath(filename);      // 루트 바로 저장
            Files.createDirectories(dst.getParent());
            Files.move(src, dst,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
            return dst;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    // 세션 종료: temp 폴더 정리
    public Mono<Void> finalizeSession(String uploadId) {
        return Mono.fromRunnable(() -> {
            Path dir = root.resolve(uploadId);
            try {
                if (Files.isDirectory(dir)) {
                    Files.walk(dir)
                            .sorted(Comparator.reverseOrder()) // 파일 → 디렉토리 순으로 삭제
                            .forEach(path -> {
                                try {
                                    Files.deleteIfExists(path);
                                } catch (IOException ignored) {
                                }
                            });
                    log.debug("removed temp dir {}", dir);
                }
            } catch (IOException e) {
                log.warn("could not clean temp dir {}", dir, e);
            }
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

}