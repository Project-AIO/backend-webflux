package com.idt.aiowebflux.service;

import com.idt.aiowebflux.dto.FileProgress;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.registry.ProgressRegistry;
import com.idt.aiowebflux.response.ChunkUploadResponse;
import com.idt.aiowebflux.session.UploadSession;
import com.idt.aiowebflux.validator.ReactiveFileValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChunkUploadService {
    private final ProgressRegistry registry;
    private final ResumableFileStorageService storage;
    private final ProgressService progressService;
    private final DocumentPersistenceService documentPersistenceService;
    private final ReactiveFileValidator reactiveFileValidator;

    public Mono<ChunkUploadResponse> executeChunkUpload(
            final String accountId,
            final String uploadId,
            final String serverFileId,
            final long offset,
            final boolean finalChunk,
            final int chunkIndex,
            final AccessLevel accessLevel,
            Flux<DataBuffer> requestBody) {
        final UploadSession session = requireSession(uploadId);
        //UploadEssionController에서 session을 생성할 때, uploadId와 serverFileId를 저장해 뒀는데
        // 여기서 그 servFileId로 파일 진행률(FileProgress)을 가져온다.
        FileProgress fp = requireFile(session, serverFileId);

        /* --- 오프셋 검증 --- */
        long received = fp.getReceivedBytes();
        if (offset > received) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    DomainExceptionCode.UPLOAD_OFFSET_MISMATCH.getMessage()
            );
        } else if (offset < received) {
            log.debug("Duplicate chunk (offset {} < received {}) for file {}", offset, received, serverFileId);
            return Mono.just(new ChunkUploadResponse(
                    fp.getFileId(), received, fp.getTotalBytes(), fp.isComplete()
            ));
        }

        session.setStatus(State.UPLOADING);

        // 본문을 하나의 DataBuffer로 합치기
        Mono<DataBuffer> bodyMono = DataBufferUtils.join(requestBody);

        return bodyMono.flatMap(buf ->
                storage.writeChunk(uploadId, serverFileId, offset, buf, finalChunk, fp.getTotalBytes())
                        .flatMap(written -> {
                            // 진행률 갱신
                            fp.addReceived(written, chunkIndex);
                            progressService.emitFile(session, fp);
                            progressService.emitOverall(session);

                            // 파일 완전 수신 시 후처리
                            Mono<Void> finalizeFileMono = Mono.empty();
                            if (finalChunk || fp.getReceivedBytes() >= fp.getTotalBytes()) {
                                finalizeFileMono =
                                        storage.finalizeFile(uploadId, serverFileId, fp.getFilename())
                                                .flatMap(path ->

                                                        reactiveFileValidator.validate(path, fp.getFilename(),
                                                                        fp.getTotalBytes(), uploadId)
                                                                .then(
                                                                        // 후처리 (DB 기록·썸네일 등)
                                                                        documentPersistenceService.executePostProcess(
                                                                                path,
                                                                                fp.getFilename(),
                                                                                session.getFolderId(),
                                                                                // 세션에 저장해 둔 값
                                                                                accountId,         // ex) "admin"
                                                                                accessLevel    // enum AccessLevel
                                                                        )
                                                                )
                                                )
                                                .doOnSuccess(v -> {
                                                    fp.markCompleted();
                                                    progressService.emitFile(session, fp);
                                                    log.debug("Finalized & persisted file {}", fp.getFilename());
                                                })
                                                .then();
                            }

                            // 세션 전체 완료 시
                            Mono<Void> finalizeSessionMono = Mono.empty();
                            if (session.allCompleted()) {
                                finalizeSessionMono =
                                        storage.finalizeSession(uploadId)
                                                .then(Mono.fromRunnable(() -> progressService.close(session)));
                            }

                            return finalizeFileMono
                                    .then(finalizeSessionMono)
                                    .thenReturn(new ChunkUploadResponse(
                                            fp.getFileId(),
                                            fp.getReceivedBytes(),
                                            fp.getTotalBytes(),
                                            fp.isComplete()
                                    ));
                        })
                        .doFinally(sig -> DataBufferUtils.release(buf)) // DataBuffer release
        );
    }

    private UploadSession requireSession(String uploadId) {
        ProgressRegistry.Entry e = registry.get(uploadId);
        if (e == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "uploadId not found");
        }
        return e.session();
    }

    private FileProgress requireFile(UploadSession s, String serverFileId) {
        FileProgress fp = s.getFile(serverFileId);
        if (fp == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "server_file_id에 해당하는 파일을 찾지 못했습니다.");
        }
        return fp;
    }
}
