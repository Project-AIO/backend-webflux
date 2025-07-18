package com.idt.aiowebflux.controller;

import com.idt.aiowebflux.dto.FileProgress;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.registry.ProgressRegistry;
import com.idt.aiowebflux.response.ChunkUploadResponse;
import com.idt.aiowebflux.response.FileStatusResponse;
import com.idt.aiowebflux.response.SessionStatusResponse;
import com.idt.aiowebflux.service.DocumentPersistenceService;
import com.idt.aiowebflux.service.ProgressService;
import com.idt.aiowebflux.service.ResumableFileStorageService;
import com.idt.aiowebflux.session.UploadSession;
import com.idt.aiowebflux.validator.ReactiveFileValidator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ChunkUploadController {

    private final ProgressRegistry registry;
    private final ResumableFileStorageService storage;
    private final ProgressService progressService;
    private final DocumentPersistenceService documentPersistenceService;
    private final ReactiveFileValidator reactiveFileValidator;

    /* -------------------------------------------------- */
    /* Helpers                                            */
    /* -------------------------------------------------- */
    private UploadSession requireSession(String uploadId) {
        ProgressRegistry.Entry e = registry.get(uploadId);
        if (e == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "uploadId not found");
        return e.session();
    }

    private FileProgress requireFile(UploadSession s, String serverFileId) {
        FileProgress fp = s.getFile(serverFileId);
        if (fp == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "file not found");
        return fp;
    }

    /* -------------------------------------------------- */
    /* Chunk Upload (offset 기반, 단일 바이너리 바디)      */
    /* -------------------------------------------------- */

    @Operation(summary = """
            벌크 업로드 실행 API
            """, description = """
               [ 벌크 업로드 실행]
               upload_id는 UploadSessionController에서 생성된 uploadId를 사용함. 즉 실행할 세션을 지정함
               server_file_id는 업로드할 파일의 ID로, UploadSessionController에서 생성된 serverFileId를 사용함
                offset은 이번 청크가 파일 전체 바이트 중 어디서부터 시작하는지를 0‑based 정수(byte)로 표현한 값
                chunkIndex는 청크의 인덱스 번호로, -1은 청크 인덱스가 없는 경우를 의미함
                finalChunk는 마지막 청크인지 여부를 나타내며, true인 경우 파일 업로드가 완료됨을 의미함
                accessLevel은 파일의 접근 수준을 나타내며, enum AccessLevel을 사용함
                Request body로는 바이너리 데이터를 일정한 chunk 단위로 전송함 프론트에서 적용한 chunk 단위로
                백엔드에서 처리하게 됨
            """)
    /**
     * offset	이번 청크가 파일 전체 바이트 중 어디서부터 시작하는지를 0‑based 정수(byte)로 표현한 값
     */
    @PutMapping(
            path = "uploads/sessions/{uploadId}/files/{serverFileId}/chunk",
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public Mono<ChunkUploadResponse> uploadChunk(ServerHttpRequest request,
                                                 @PathVariable final String uploadId,
                                                 @PathVariable final String serverFileId,
                                                 @RequestParam("offset") final long offset,
                                                 @RequestParam(name = "chunk_index", defaultValue = "-1") final int chunkIndex,
                                                 @RequestParam(name = "final", defaultValue = "false") final boolean finalChunk,
                                                 @RequestParam(name = "access_level") final AccessLevel accessLevel) {

        final UploadSession session = requireSession(uploadId);
        //UploadEssionController에서 session을 생성할 때, uploadId와 serverFileId를 저장해 뒀는데
        // 여기서 그 servFileId로 파일 진행률(FileProgress)을 가져온다.
        FileProgress fp = requireFile(session, serverFileId);

        /* --- 오프셋 검증 --- */
        long received = fp.getReceivedBytes();
        if (offset > received) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    DomainExceptionCode.UPLOAD_OFFSET_MISMATCH.name()
            );
        } else if (offset < received) {
            log.debug("Duplicate chunk (offset {} < received {}) for file {}", offset, received, serverFileId);
            return Mono.just(new ChunkUploadResponse(
                    fp.getFileId(), received, fp.getTotalBytes(), fp.isComplete()
            ));
        }

        session.setStatus(State.UPLOADING);

        /* --- 본문을 하나의 DataBuffer로 합치기 --- */
        Mono<DataBuffer> bodyMono = DataBufferUtils.join(request.getBody());

        return bodyMono.flatMap(buf ->
                storage.writeChunk(uploadId, serverFileId, offset, buf, finalChunk, fp.getTotalBytes())
                        .flatMap(written -> {
                            /* -------- 진행률 갱신 -------- */
                            fp.addReceived(written, chunkIndex);
                            progressService.emitFile(session, fp);
                            progressService.emitOverall(session);

                            /* -------- 파일 완전 수신 시 후처리 -------- */
                            Mono<Void> finalizeFileMono = Mono.empty();
                            if (finalChunk || fp.getReceivedBytes() >= fp.getTotalBytes()) {
                                finalizeFileMono =
                                        storage.finalizeFile(uploadId, serverFileId, fp.getFilename())
                                                .flatMap(path ->

                                                        reactiveFileValidator.validate(path, fp.getFilename(), fp.getTotalBytes())
                                                                .then(
                                                                        /* 후처리 (DB 기록·썸네일 등) */
                                                                        documentPersistenceService.executePostProcess(
                                                                                path,
                                                                                fp.getFilename(),
                                                                                session.getFolderId(),      // 세션에 저장해 둔 값
                                                                                "admin",         // ex) "admin"
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

                            /* -------- 세션 전체 완료 시 -------- */
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
}