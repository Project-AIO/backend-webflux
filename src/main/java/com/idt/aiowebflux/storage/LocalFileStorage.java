package com.idt.aiowebflux.storage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class LocalFileStorage implements FileStorage {
    public static final String DOC_ROOT = File.separator + "aio";
    public static final String ROOT_PATH = System.getProperty("user.dir");
    public static final String UPLOAD_PATH = ROOT_PATH + DOC_ROOT;
    private final Path rootDir;

    public LocalFileStorage() throws IOException {
        this.rootDir = Paths.get(UPLOAD_PATH).toAbsolutePath().normalize();
        Files.createDirectories(rootDir);
    }

    @Override
    public Mono<Path> save(String relativePath, Flux<DataBuffer> content, long expectedSize) {
        Path dest = rootDir.resolve(relativePath).normalize();

        return Mono.fromCallable(() -> {
                    Files.createDirectories(dest.getParent()); // blocking
                    return dest;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(p ->
                        DataBufferUtils.write(content, p,
                                        StandardOpenOption.CREATE,
                                        StandardOpenOption.TRUNCATE_EXISTING)
                                .thenReturn(p)
                );
    }

    @Override
    public Mono<Resource> load(String relativePath) {
        Path p = rootDir.resolve(relativePath).normalize();
        return Mono.just(new FileSystemResource(p));
    }
}
