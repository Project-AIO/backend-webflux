package com.idt.aiowebflux.storage;


import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
public interface FileStorage {
    /**
     * 지정 경로에 DataBuffer 스트림을 저장.
     */
    Mono<Path> save(String relativePath, Flux<DataBuffer> content, long expectedSize);
    Mono<Resource> load(String relativePath);
}
