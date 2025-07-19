package com.idt.aiowebflux.session;

import com.idt.aiowebflux.dto.FileProgress;
import com.idt.aiowebflux.entity.constant.State;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UploadSession {

    private final String uploadId;   // 서버 생성 UUID
    private final Long folderId;   // 사용자가 선택한 폴더 (nullable)
    private final Instant createdAt = Instant.now();
    // serverFileId -> FileProgress
    private final Map<String, FileProgress> files = new ConcurrentHashMap<>();
    private volatile State status = State.NEW;

    public UploadSession(String uploadId, Long folderId) {
        this.uploadId = uploadId;
        this.folderId = folderId;
    }

    public UploadSession(String uploadId, Long folderId, State status) {
        this.uploadId = uploadId;
        this.folderId = folderId;
        this.status = status;
    }

    public String getUploadId() {
        return uploadId;
    }

    public Long getFolderId() {
        return folderId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public State getStatus() {
        return status;
    }

    public void setStatus(State s) {
        this.status = s;
    }

    public void addFile(FileProgress fp) {
        files.put(fp.getFileId(), fp);
    }

    public FileProgress getFile(String serverFileId) {
        return files.get(serverFileId);
    }

    /**
     * 클라 clientFileId로 조회 필요할 때.
     */
    public FileProgress getFileByClientId(String clientFileId) {
        return files.values().stream()
                .filter(fp -> fp.getClientFileId().equals(clientFileId))
                .findFirst().orElse(null);
    }

    public Collection<FileProgress> getFiles() {
        return files.values();
    }

    /**
     * 모든 파일이 완료되었는지.
     */
    public boolean allCompleted() {
        return files.values().stream().allMatch(FileProgress::isComplete);
    }

    public long totalBytes() {
        return files.values().stream().mapToLong(FileProgress::getTotalBytes).sum();
    }

    public long receivedBytes() {
        return files.values().stream().mapToLong(FileProgress::getReceivedBytes).sum();
    }

    public double percent() {
        long tot = totalBytes();
        return tot > 0 ? (receivedBytes() * 100.0) / tot : 0.0;
    }
}

