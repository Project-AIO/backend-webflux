package com.idt.aiowebflux.emitter;

import com.idt.aiowebflux.dto.ProgressEventDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class ProgressEmitter {

    private final Sinks.Many<ProgressEventDto> sink =
            Sinks.many().replay().limit(3);

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

