package com.idt.aiowebflux.service;


import com.idt.aiowebflux.dto.FileProgress;
import com.idt.aiowebflux.dto.ProgressEventDto;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.registry.ProgressRegistry;
import com.idt.aiowebflux.session.UploadSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRegistry registry;
    private final ResumableFileStorageService storage;

    // SSE 구독
    public Flux<ProgressEventDto> flux(String uploadId) {
        ProgressRegistry.Entry e = registry.get(uploadId);
        return e == null ? Flux.empty() : e.emitter().flux();
    }

    // 파일 청크 수신 후 emit
    public void emitFile(UploadSession session, FileProgress fp) {
        ProgressRegistry.Entry e = registry.get(session.getUploadId());
        if (e != null) {
            e.emitter().emit(ProgressEventDto.file(session.getUploadId(), fp));
        }
    }

    // 전체 진행률 emit
    public void emitOverall(UploadSession session) {
        ProgressRegistry.Entry e = registry.get(session.getUploadId());
        if (e != null) {
            e.emitter().emit(ProgressEventDto.overall(
                    session.getUploadId(),
                    session.receivedBytes(),
                    session.totalBytes(),
                    session.percent(),
                    session.getStatus(),
                    null
            ));
        }
    }

    /**
     * 서버 디스크 기준으로 진행률 동기화. 페이지 이탈 후 resume 전에 호출 추천.
     */
    public Mono<Void> reconcileSession(UploadSession session) {
        return Flux.fromIterable(session.getFiles())
                .flatMap(fp -> storage.size(session.getUploadId(), fp.getFileId())
                        .doOnNext(fp::syncReceivedBytes)
                        .then())
                .then(Mono.fromRunnable(() -> {
                    emitOverall(session);
                    session.getFiles().forEach(fp -> emitFile(session, fp));
                }));
    }

    public void close(UploadSession session) {
        session.setStatus(State.COMPLETE);
        emitOverall(session);
        ProgressRegistry.Entry e = registry.remove(session.getUploadId());
        if (e != null) {
            e.emitter().sink().tryEmitComplete();
        }
    }

    public void failAndClose(UploadSession session, Throwable t) {
        session.setStatus(State.ERROR);
        emitOverall(session);
        ProgressRegistry.Entry e = registry.remove(session.getUploadId());
        if (e != null) {
            e.emitter().sink().tryEmitError(t);
        }
    }
}
