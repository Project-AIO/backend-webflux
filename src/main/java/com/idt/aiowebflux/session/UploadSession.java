package com.idt.aiowebflux.session;

import com.idt.aiowebflux.dto.FileProgress;
import com.idt.aiowebflux.entity.constant.State;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
public class UploadSession {

    private final String uploadId;   // 서버 생성 UUID
    private final Long   folderId;   // 사용자가 선택한 폴더 (nullable)
    private final Instant createdAt = Instant.now();

    private volatile State status = State.NEW;

    // serverFileId -> FileProgress
    private final Map<String, FileProgress> files = new ConcurrentHashMap<>();

    public UploadSession(String uploadId, Long folderId) {
        this.uploadId = uploadId;
        this.folderId = folderId;
    }

    public UploadSession(String uploadId, Long folderId, State status) {
        this.uploadId = uploadId;
        this.folderId = folderId;
        this.status = status;
    }

    public String getUploadId() { return uploadId; }
    public Long getFolderId()   { return folderId; }
    public Instant getCreatedAt(){ return createdAt; }

    public State getStatus() { return status; }
    public void setStatus(State s) { this.status = s; }

    public void addFile(FileProgress fp) {
        files.put(fp.getFileId(), fp);
    }

    public FileProgress getFile(String serverFileId) {
        return files.get(serverFileId);
    }

    /** 클라 clientFileId로 조회 필요할 때. */
    public FileProgress getFileByClientId(String clientFileId) {
        return files.values().stream()
                .filter(fp -> fp.getClientFileId().equals(clientFileId))
                .findFirst().orElse(null);
    }

    public Collection<FileProgress> getFiles() {
        return files.values();
    }

    /** 모든 파일이 완료되었는지. */
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
/*
@Getter
public class UploadSession {
    private final String uploadId;
    private final long folderId;
    private final Instant createdAt = Instant.now();
    private final Map<String, FileProgress> files = new ConcurrentHashMap<>();
    private final AtomicReference<State> status = new AtomicReference<>(State.PENDING);

    public UploadSession(String uploadId, long folderId) {
        this.uploadId = uploadId;
        this.folderId = folderId;
    }
    public Collection<FileProgress> getAllFiles() { return files.values(); }
    public Optional<FileProgress> getFile(String fileId) { return Optional.ofNullable(files.get(fileId)); }
    public State getStatus() { return status.get(); }
    public void addFile(FileProgress fp) { files.put(fp.getFileId(), fp); }

    public long getTotalBytes() {
        return files.values().stream().mapToLong(FileProgress::getTotalBytes).filter(b -> b > 0).sum();
    }
    public long getReceivedBytes() {
        return files.values().stream().mapToLong(FileProgress::getReceivedBytes).sum();
    }
    public int getOverallPercent() {
        long total = getTotalBytes();
        if (total <= 0) return -1;
        return (int) Math.min(100, Math.round((getReceivedBytes() * 100.0) / total));
    }

    public void setStatus(State status) {
        this.status.set(status);
    }
}
*/
