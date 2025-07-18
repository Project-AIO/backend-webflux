package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.State;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
public class FileProgress {

    private final String fileId;        // 서버 내부 파일 식별자 (UUID)
    private final String clientFileId;  // 프론트에서 보낸 UUID
    private final String filename;
    private final long   totalBytes;

    private final AtomicLong receivedBytes = new AtomicLong(0L);
    private final AtomicReference<State> status = new AtomicReference<>(State.NEW);
    private volatile int lastChunkIndex = -1;
    private volatile String errorMessage;

    public FileProgress(String fileId, String clientFileId, String filename, long totalBytes) {
        this.fileId = fileId;
        this.clientFileId = clientFileId;
        this.filename = filename;
        this.totalBytes = totalBytes;
    }

    // --- getters
    public String getFileId()       { return fileId; }
    public String getClientFileId() { return clientFileId; }
    public String getFilename()     { return filename; }
    public long   getTotalBytes()   { return totalBytes; }
    public long   getReceivedBytes(){ return receivedBytes.get(); }
    public State  getStatus()       { return status.get(); }
    public int    getLastChunkIndex(){ return lastChunkIndex; }
    public String getErrorMessage() { return errorMessage; }

    // --- progress update
    public long addReceived(long delta, int chunkIndex) {
        long v = receivedBytes.addAndGet(delta);
        if (chunkIndex > lastChunkIndex) {
            lastChunkIndex = chunkIndex;
        }
        if (v >= totalBytes) {
            markCompleted();
        } else {
            status.compareAndSet(State.NEW, State.UPLOADING);
        }
        return v;
    }

    /** 서버 파일 시스템 scan 등으로 실제 바이트에 맞춰 강제 동기화. */
    public void syncReceivedBytes(long actualBytes) {
        receivedBytes.set(actualBytes);
        if (actualBytes >= totalBytes) {
            markCompleted();
        } else if (status.get() == State.NEW) {
            status.set(State.UPLOADING);
        }
    }

    public void markCompleted() {
        status.set(State.COMPLETE);
    }

    public void markError(String message) {
        errorMessage = message;
        status.set(State.ERROR);
    }

    public double percent() {
        long tot = totalBytes;
        return tot > 0 ? (getReceivedBytes() * 100.0) / tot : 0.0;
    }

    public boolean isComplete() {
        return getStatus() == State.COMPLETE;
    }
}

/*@Getter
public final class FileProgress {
    private final String fileId;      // 서버 발급 고유 ID
    private final String filename;    // 클라이언트 파일명
    private final long totalBytes;    // 알려진 경우; 모르면 -1
    private final AtomicLong receivedBytes = new AtomicLong();
    private final AtomicReference<State> status = new AtomicReference<>(State.PENDING);
    private volatile String errorMessage;

    public FileProgress(String fileId, String filename, long totalBytes) {
        this.fileId = fileId;
        this.filename = filename;
        this.totalBytes = totalBytes;
    }
    public long getReceivedBytes() { return receivedBytes.get(); }
    public State getStatus() { return status.get(); }
    public String getErrorMessage() { return errorMessage; }
    public void addBytes(long n) { receivedBytes.addAndGet(n); }
    public void markInProgress() { status.set(State.IN_PROGRESS); }
    public void markCompleted() { status.set(State.COMPLETE); }
    public void markFailed(String msg) { errorMessage = msg; status.set(State.FAILED); }
    public void markCanceled() { status.set(State.CANCELLED); }

    public int getPercent() {
        if (totalBytes <= 0) return -1;
        long rec = receivedBytes.get();
        return (int) Math.min(100, Math.round((rec * 100.0) / totalBytes));
    }
}*/
