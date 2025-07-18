package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.State;

public record ProgressEventDto(
        String type,          // \"file\" | \"overall\"
        String uploadId,
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
                fp.getFileId(),
                fp.getFilename(),
                fp.getReceivedBytes(),
                fp.getTotalBytes(),
                fp.percent(),
                fp.getStatus(),
                fp.getErrorMessage()
        );
    }

    public static ProgressEventDto overall(String uploadId, long received, long total, double pct, State status, String error) {
        return new ProgressEventDto(
                "overall",
                uploadId,
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