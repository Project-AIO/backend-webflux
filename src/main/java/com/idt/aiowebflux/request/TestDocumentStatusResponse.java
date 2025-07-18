package com.idt.aiowebflux.request;

import com.idt.aiowebflux.entity.constant.State;

public record TestDocumentStatusResponse(
        String subscribe_key,
        State state,
        String doc_id,
        Integer progress,
        String error_trace,
        long timestamp
) {
}
