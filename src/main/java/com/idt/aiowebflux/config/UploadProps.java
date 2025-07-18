package com.idt.aiowebflux.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(prefix = "upload")
public class UploadProps {
    public static final String DOC_ROOT = File.separator + "aio";
    public static final String ROOT_PATH = System.getProperty("user.dir");
    public static final String UPLOAD_PATH = ROOT_PATH + DOC_ROOT;
    private final Storage storage = new Storage();
    private final Progress progress = new Progress();

    public Storage getStorage() { return storage; }
    public Progress getProgress() { return progress; }

    public static class Storage {
        private String rootDir = UPLOAD_PATH;
        public String getRootDir() { return rootDir; }
        public void setRootDir(String rootDir) { this.rootDir = rootDir; }
    }

    public static class Progress {
        private long emitIntervalMs = 250;
        private long emitBytesThreshold = 1024 * 1024; // 1MB
        private long sessionTtlMinutes = 30;
        public long getEmitIntervalMs() { return emitIntervalMs; }
        public void setEmitIntervalMs(long emitIntervalMs) { this.emitIntervalMs = emitIntervalMs; }
        public long getEmitBytesThreshold() { return emitBytesThreshold; }
        public void setEmitBytesThreshold(long emitBytesThreshold) { this.emitBytesThreshold = emitBytesThreshold; }
        public long getSessionTtlMinutes() { return sessionTtlMinutes; }
        public void setSessionTtlMinutes(long sessionTtlMinutes) { this.sessionTtlMinutes = sessionTtlMinutes; }
    }
}
