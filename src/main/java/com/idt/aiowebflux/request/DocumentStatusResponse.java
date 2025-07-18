package com.idt.aiowebflux.request;

import com.idt.aiowebflux.entity.constant.State;

public record DocumentStatusResponse(
        String subscribeKey,
        State state,
        String docId,
        Integer queueSize,
        Integer progress,
        String errorTrace,
        long timestamp
) {
}
