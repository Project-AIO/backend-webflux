package com.idt.aiowebflux.controller;

import com.idt.aiowebflux.registry.ProgressRegistry;
import com.idt.aiowebflux.repository.FolderRepository;
import com.idt.aiowebflux.response.ChunkUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class TestController {

    private final WebClient webClient; // baseUrl 주입 권장

    /**
     * 간이 셀프-테스트:
     *  로컬 파일을 읽어 20개 청크로 나눈 뒤 ChunkUploadController로 순차 PUT.
     *  - uploadId / serverFileId 는 쿼리 파라미터로 받음 (이미 세션이 생성돼 있어야 함)
     *  - 실제 서비스 로직엔 쓰지 마세요 (오직 테스트용)
     */

    @GetMapping(value = "/sse/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TestResult> testSse( @RequestParam("upload_id") String uploadId,
                                     @RequestParam("server_file_id") String serverFileId,
                                     @RequestParam("path") String path // 기본값 테스트 파일
    ) throws IOException {

        // 1) 테스트용 파일 경로
        Path filePath = Path.of(path);
        if (!Files.exists(filePath)) {
            return Mono.error(new IllegalArgumentException("File not found: " + path));
        }
        // 2) 파일을 바이트 배열로 읽기
        byte[] bytes = Files.readAllBytes(filePath);

        final int CHUNKS = 20;
        final int chunkSize = (int) Math.ceil((double) bytes.length / CHUNKS);

        // 2) Flux로 청크 스트림 생성 (offset, index, isFinal, payload)
        Flux<ChunkSpec> chunkFlux = Flux.range(0, CHUNKS)
                .map(i -> {
                    int offset = i * chunkSize;
                    if (offset >= bytes.length) { // 안전
                        return new ChunkSpec(i, offset, true, new byte[0]);
                    }
                    int end = Math.min(offset + chunkSize, bytes.length);
                    boolean isFinal = end >= bytes.length;
                    byte[] part = Arrays.copyOfRange(bytes, offset, end);
                    return new ChunkSpec(i, offset, isFinal, part);
                })
                // 실제 길이가 0인 더미 청크는 제거
                .filter(spec -> spec.part().length > 0);

        // 3) 순차 호출 (concatMap)
        return chunkFlux.concatMap(spec ->
                        sendOneChunk(uploadId, serverFileId, spec)
                                .doOnNext(resp -> log.info(
                                        "chunk {} sent (offset={} size={} final={}) -> {} bytes received={}",
                                        spec.index(), spec.offset(), spec.part().length, spec.isFinal(),
                                        resp.serverFileId(), resp.receivedBytes()
                                ))
                )
                .collectList()
                .map(list -> new TestResult(bytes.length, list.size(), list));
    }

    /* 개별 청크 전송 */
    private Mono<ChunkUploadResponse> sendOneChunk(String uploadId,
                                                   String serverFileId,
                                                   ChunkSpec spec) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8100)
                        .path("/api/v1/uploads/sessions/{u}/files/{f}/chunk")
                        .queryParam("offset", spec.offset())
                        .queryParam("chunk_index", spec.index())
                        .queryParam("final", spec.isFinal())
                        .queryParam("access_level", AccessLevel.PUBLIC) // 도메인 enum
                        .build(uploadId, serverFileId))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImp0aSI6Ijc4OGExNWVkLTVjNTEtNDdlNC04NTM0LTA0MTgxODQ4ZjFkNCIsImFjY291bnRfaWQiOiJhZG1pbiIsImlzcyI6IkFJTyIsInJvbGVzIjpbIkFETUlOIl0sImV4cCI6MTgxMzA1NDY5M30.1I_3zoH-UDTnVkVgIa6TwEsIXXbeNG8azh8231o9PH55Z_ZcISW1juI6l2YpeS0rqjRTnc1wYC0Gbfnm6xsrmA")
                .bodyValue(spec.part()) // 작은 테스트라 byte[] 사용
                .retrieve()
                .bodyToMono(ChunkUploadResponse.class);
    }

    /* 테스트 응답 DTO */
    public record TestResult(
            long totalBytes,
            int chunksSent,
            java.util.List<ChunkUploadResponse> responses
    ) {}

    /* 내부 청크 메타 */
    private record ChunkSpec(
            int index,
            int offset,
            boolean isFinal,
            byte[] part
    ) {}
}