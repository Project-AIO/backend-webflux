package com.idt.aiowebflux.response;

import com.idt.aiowebflux.dto.FileProgress;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.session.UploadSession;

public record SessionStatusResponse(String uploadId,
                                    State status,
                                    long overallReceived,
                                    long overallTotal,
                                    int completedCount,
                                    int fileCount) {
    public static SessionStatusResponse from(UploadSession s) {
        long recv = s.getFiles().stream().mapToLong(FileProgress::getReceivedBytes).sum();
        long tot  = s.getFiles().stream().mapToLong(FileProgress::getTotalBytes).sum();
        int done  = (int)s.getFiles().stream().filter(FileProgress::isComplete).count();
        return new SessionStatusResponse(s.getUploadId(), s.getStatus(), recv, tot, done, s.getFiles().size());
    }
    public static SessionStatusResponse notFound(String uploadId) {
        return new SessionStatusResponse(uploadId, State.ERROR, 0,0,0,0);
    }
}
