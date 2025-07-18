package com.idt.aiowebflux.response;

import com.idt.aiowebflux.dto.FileProgress;
import com.idt.aiowebflux.entity.constant.State;

public record FileStatusResponse(
        String serverFileId,
        String clientFileId,
        String filename,
        long resumeOffset,
        long receivedBytes,
        long totalBytes,
        int lastChunkIndex,
        State status,
        boolean complete,
        Double percent,
        String errorMessage
) {
    public static FileStatusResponse from(FileProgress fp) {
        long recv = fp.getReceivedBytes();
        boolean complete = fp.isComplete();
        return new FileStatusResponse(
                fp.getFileId(),
                fp.getClientFileId(),
                fp.getFilename(),
                recv,     // resumeOffset == receivedBytes
                recv,
                fp.getTotalBytes(),
                fp.getLastChunkIndex(),
                fp.getStatus(),
                complete,
                fp.percent(),
                fp.getErrorMessage()
        );
    }

    public static FileStatusResponse notFound(String serverFileId, String clientFileId) {
        return new FileStatusResponse(
                serverFileId,
                clientFileId,
                null,
                0L,
                0L,
                0L,
                -1,
                State.ERROR,
                false,
                0.0,
                "FILE_NOT_FOUND"
        );
    }
}
