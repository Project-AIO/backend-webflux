package com.idt.aiowebflux.controller;

import com.idt.aiowebflux.dto.ProgressEventDto;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.registry.ProgressRegistry;
import com.idt.aiowebflux.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/upload-sessions")
public class UploadSseController {
    private final ProgressRegistry registry;
    private final ProgressService progressService;

    public UploadSseController(ProgressRegistry registry, ProgressService progressService) {
        this.registry = registry;
        this.progressService = progressService;
    }

    @Operation(summary = """
            벌크 업로드 세션 SSE 구독 API
            """, description = """
               [벌크 업로드 세션 SSE 구독]
               UploadSessionController에서 벌크 업로드 API에서 받은 response의 upload_id를 이용하여
               구독을 신청
            """)
    @GetMapping(path = "/{upload_id}/progress", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ProgressEventDto>> stream(@PathVariable("upload_id") final String uploadId) {
        final ProgressRegistry.Entry e = registry.get(uploadId);
        if (e == null) {
            return Flux.just(ServerSentEvent.<ProgressEventDto>builder()
                    .event("error")
                    .data(new ProgressEventDto(uploadId, null, null, null, 0, -1, 0.0,State.FAILED, "uploadId not found"))
                    .build());
        }

        // overall : 전체 업로드 진행률
        // file : 개별 파일 업로드 진행률
        return progressService.flux(uploadId)
                .map(ev -> ServerSentEvent.<ProgressEventDto>builder()
                        .event(ev.fileId() == null ? "overall" : "file")
                        .data(ev)
                        .build());
    }
}
