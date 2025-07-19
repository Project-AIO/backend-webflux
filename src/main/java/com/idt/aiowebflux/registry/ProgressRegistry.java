package com.idt.aiowebflux.registry;

import com.idt.aiowebflux.emitter.ProgressEmitter;
import com.idt.aiowebflux.session.UploadSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProgressRegistry {

    private final ConcurrentMap<String, Entry> map = new ConcurrentHashMap<>();

    public Entry create(UploadSession s) {
        Entry e = new Entry(s, new ProgressEmitter());
        map.put(s.getUploadId(), e);
        return e;
    }

    public Entry get(String uploadId) {
        return map.get(uploadId);
    }

    public Entry remove(String uploadId) {
        return map.remove(uploadId);
    }

    public record Entry(UploadSession session, ProgressEmitter emitter) {
    }
}
/*
public final class ProgressRegistry {

    public static final class Entry {
        private final UploadSession session;
        private final ProgressEmitter emitter;

        Entry(UploadSession s, ProgressEmitter e) {
            this.session = s;
            this.emitter = e;
        }

        public UploadSession session() {
            return session;
        }

        public ProgressEmitter emitter() {
            return emitter;
        }
    }

    private final Map<String, Entry> sessions = new ConcurrentHashMap<>();
    private final long emitIntervalMs;
    private final long emitBytesThreshold;

    public ProgressRegistry(UploadProps props) {
        this.emitIntervalMs = props.getProgress().getEmitIntervalMs();
        this.emitBytesThreshold = props.getProgress().getEmitBytesThreshold();
    }

    public Entry create(UploadSession session) {
        ProgressEmitter emitter = new ProgressEmitter(emitIntervalMs, emitBytesThreshold);
        Entry e = new Entry(session, emitter);
        sessions.put(session.getUploadId(), e);
        return e;
    }

    public Entry get(String uploadId) {
        return sessions.get(uploadId);
    }

    public Entry remove(String uploadId) {
        return sessions.remove(uploadId);
    }
}
*/
