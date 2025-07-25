package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.AccessModifier;
import com.idt.aiowebflux.entity.constant.State;

public record ProgressEventDto(
        String type,          // \"file\" | \"overall\"
        String uploadId,
        Long folderId,
        AccessModifier accessModifier,
        String fileId,        // file 이벤트일 때만
        String filename,      // 선택
        long receivedBytes,
        long totalBytes,
        Double percent,
        State status,
        String errorMessage
) {
    public static ProgressEventDto file(String uploadId, FileProgress fp) {
        return new ProgressEventDto(
                "file",
                uploadId,
                fp.getFolderId(),
                fp.getAccessModifier(),
                fp.getFileId(),
                fp.getFilename(),
                fp.getReceivedBytes(),
                fp.getTotalBytes(),
                fp.percent(),
                fp.getStatus(),
                fp.getErrorMessage()
        );
    }

    public static ProgressEventDto overall(final String uploadId, final Long folderId, final AccessModifier accessModifier, final long received, final long total, final double pct, final State status,
                                           final String error) {
        return new ProgressEventDto(
                "overall",
                uploadId,
                folderId,
                accessModifier,
                null,
                null,
                received,
                total,
                pct,
                status,
                error
        );
    }
}