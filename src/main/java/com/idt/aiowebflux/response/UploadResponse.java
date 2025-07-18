package com.idt.aiowebflux.response;

import java.util.List;

public record UploadResponse(
    String uploadId,
    int totalFileCount,
    List<FailedFileDetails> failedFiles

) {
}
