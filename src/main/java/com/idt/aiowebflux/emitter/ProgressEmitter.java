package com.idt.aiowebflux.emitter;

import com.idt.aiowebflux.dto.ProgressEventDto;
import com.idt.aiowebflux.entity.constant.State;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProgressEmitter {

    private final Sinks.Many<ProgressEventDto> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    public void emit(ProgressEventDto ev) {
        sink.tryEmitNext(ev);
    }

    public Flux<ProgressEventDto> flux() {
        return sink.asFlux();
    }

    public Sinks.Many<ProgressEventDto> sink() {
        return sink;
    }
}
/*
public final  class ProgressEmitter {
    private final Sinks.Many<ProgressEventDto> sink =
            Sinks.many().multicast().onBackpressureBuffer();

    private final long minIntervalMs;
    private final long bytesThreshold;

    // key: fileId(NULL->overall) -> last emit snapshot
    private final Map<String, Snapshot> last = new ConcurrentHashMap<>();

    public ProgressEmitter(long minIntervalMs, long bytesThreshold) {
        this.minIntervalMs = minIntervalMs;
        this.bytesThreshold = bytesThreshold;
    }

    public Sinks.Many<ProgressEventDto> sink() {
        return sink;
    }

    public void emit(ProgressEventDto ev) {
        String key = ev.fileId() == null ? "*" : ev.fileId();
        long now = System.currentTimeMillis();
        Snapshot prev = last.get(key);
        if (prev != null) {
            boolean timeOk = (now - prev.ts) >= minIntervalMs;
            boolean bytesOk = Math.abs(ev.receivedBytes() - prev.bytes) >= bytesThreshold;
            boolean statusChanged = ev.status() != prev.status;
            if (!(timeOk || bytesOk || statusChanged)) {
                return; // skip
            }
        }
        last.put(key, new Snapshot(now, ev.receivedBytes(), ev.status()));
        sink.tryEmitNext(ev);
    }



    private record Snapshot(long ts, long bytes, State status) {}
}
*/
