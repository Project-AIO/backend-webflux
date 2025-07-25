package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.AccessModifier;

import java.util.List;

public record CreateUploadSessionResponse(
        String uploadId,
        List<RegisteredFile> files
) {
    // ↓ filename 추가
    public record RegisteredFile(
            String clientFileId,
            Long folderId,
            AccessModifier accessModifier,
            String serverFileId,
            String filename,
            long size
    ) {
    }
}