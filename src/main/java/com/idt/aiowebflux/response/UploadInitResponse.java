package com.idt.aiowebflux.response;

public record UploadInitResponse(
        String uploadId,
        int total
) {
}
