package com.idt.aiowebflux.response;

public record FailedFileDetails(
    String fileName,
    String errorMessage
) {
}
