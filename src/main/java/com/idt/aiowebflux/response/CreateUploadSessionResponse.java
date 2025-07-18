package com.idt.aiowebflux.response;

import java.util.List;

public record CreateUploadSessionResponse(
        String uploadId,
        List<RegisteredFile> files
) {
    // ↓ filename 추가
    public record RegisteredFile(
            String clientFileId,
            String serverFileId,
            String filename,
            long size
    ) {}
}