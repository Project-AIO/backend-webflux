/*
package com.idt.aiowebflux.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idt.aiowebflux.dto.FileProgress;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.registry.ProgressRegistry;
import com.idt.aiowebflux.request.UploadMetaPart;
import com.idt.aiowebflux.service.DocumentPersistenceService;
import com.idt.aiowebflux.service.ProgressService;
import com.idt.aiowebflux.session.UploadSession;
import com.idt.aiowebflux.storage.FileStorage;
import com.idt.aiowebflux.validator.ReactiveFileValidator;
import lombok.AccessLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/folders")
public class UploadController {


    private final ProgressRegistry registry;
    private final ProgressService progressService;
    private final FileStorage storage;
    private final ReactiveFileValidator reactiveFileValidator;
    private final DocumentPersistenceService documentPersistenceService;

    public UploadController(ProgressRegistry registry,
                            ProgressService progressService,
                            FileStorage storage,
                            ReactiveFileValidator reactiveFileValidator,
                            DocumentPersistenceService documentPersistenceService) {
        this.registry = registry;
        this.progressService = progressService;
        this.storage = storage;
        this.reactiveFileValidator = reactiveFileValidator;
        this.documentPersistenceService = documentPersistenceService;
    }

    @PostMapping(
            path = "/{folderId}/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<UploadResultResponse> upload(@PathVariable("folderId") Long folderId,
                                             @RequestParam("uploadId") String uploadId,
                                             @RequestParam("access_level") AccessLevel accessLevel,
                                             @RequestPart("meta") Mono<UploadMetaPart> metaMono,
                                             @RequestPart("files") Flux<FilePart> files) {

        ProgressRegistry.Entry entry = registry.get(uploadId);
        if (entry == null) {
            return Mono.error(new IllegalArgumentException("Unknown uploadId"));
        }
        UploadSession session = entry.session();
        session.setStatus(State.IN_PROGRESS);

        Mono<Map<String, String>> metaMapMono = metaMono.map(UploadMetaPart::toFilenameMap);

        return metaMapMono
                .flatMapMany(metaMap -> files.flatMap(filePart -> handleOneFile(session, filePart, metaMap, folderId, accessLevel)))
                .then(Mono.fromCallable(() -> {
                    session.setStatus(State.COMPLETE);
                    progressService.emitOverall(session);
                    progressService.completeAndClose(session);
                    return new UploadResultResponse(session.getUploadId(), "OK");
                }))
                .onErrorResume(ex -> {
                    log.error("Bulk upload error", ex);
                    session.setStatus(State.FAILED);
                    progressService.emitOverall(session);
                    progressService.completeAndClose(session);
                    return Mono.just(new UploadResultResponse(session.getUploadId(), "FAILED: " + ex.getMessage()));
                });
    }

    private Mono<Void> handleOneFile(UploadSession session,
                                     FilePart filePart,
                                     Map<String, String> metaMap,
                                     Long folderId,
                                     AccessLevel accessLevel) {

        //확장자 포함
        String filename = filePart.filename();
        String serverFileId = metaMap.get(filename);

        final FileProgress fp;
        if (serverFileId != null) {
            fp = session.getFile(serverFileId)
                    .orElseGet(() -> {
                        // fallback: 등록 안된 파일 → 즉석 등록
                        FileProgress nf = new FileProgress(
                                serverFileId,
                                filename,
                                filePart.headers().getContentLength()
                        );
                        session.addFile(nf);
                        return nf;
                    });
        } else {
            // 파일명만 전달된 경우 (비권장) → UUID 생성
            fp = new FileProgress(
                    java.util.UUID.randomUUID().toString(),
                    filename,
                    filePart.headers().getContentLength()
            );
            session.addFile(fp);
        }

        fp.markInProgress();
        progressService.emitFile(session, fp);
        progressService.emitOverall(session);

        Flux<org.springframework.core.io.buffer.DataBuffer> measured =
                filePart.content().doOnNext(db -> {
                    int n = db.readableByteCount();
                    fp.addBytes(n);
                    progressService.emitFile(session, fp);
                    progressService.emitOverall(session);
                });

        //doc id로 파일 이름 생성
        String fileName = fp.getFileId() + "_" + fp.getFilename();

        return storage.save(fileName, measured, fp.getTotalBytes())
                .flatMap(savedPath ->
                        reactiveFileValidator.validate(savedPath, filename, fp.getTotalBytes())
                                .then(documentPersistenceService.executePostProcess(savedPath, fp.getFilename(), folderId, "admin", accessLevel))
                                .thenReturn(savedPath)
                )
                .doOnSuccess(path -> {
                    fp.markCompleted();
                    progressService.emitFile(session, fp);
                    progressService.emitOverall(session);
                })
                .doOnError(err -> {
                    fp.markFailed(err.getMessage());
                    progressService.emitFile(session, fp);
                    progressService.emitOverall(session);
                })
                .then();
    }

    public record UploadResultResponse(String uploadId, String status) {
    }
}*/
