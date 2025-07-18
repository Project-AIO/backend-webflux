package com.idt.aiowebflux.request;

public record FileDownloadResult(
        String fileName,
        byte[] content,
        String contentType
) {
}
