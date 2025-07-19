package com.idt.aiowebflux.controller;

import com.idt.aiowebflux.annotation.JwtContext;
import com.idt.aiowebflux.dto.JwtPrincipal;
import com.idt.aiowebflux.response.ChunkUploadResponse;
import com.idt.aiowebflux.service.ChunkUploadService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ChunkUploadController {

    private final ChunkUploadService chunkUploadService;

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
                                                 @JwtContext final JwtPrincipal jwtPrincipal,
                                                 @PathVariable final String uploadId,
                                                 @PathVariable final String serverFileId,
                                                 @RequestParam("offset") final long offset,
                                                 @RequestParam(name = "chunk_index", defaultValue = "-1") final int chunkIndex,
                                                 @RequestParam(name = "final", defaultValue = "false") final boolean finalChunk,
                                                 @RequestParam(name = "access_level") final AccessLevel accessLevel) {

        return chunkUploadService.executeChunkUpload(
                jwtPrincipal.accountId(),
                uploadId,
                serverFileId,
                offset,
                finalChunk,
                chunkIndex,
                accessLevel,
                request.getBody()
        );
    }
}