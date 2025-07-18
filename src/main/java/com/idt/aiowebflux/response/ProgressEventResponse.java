package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.State;

public record ProgressEventResponse(
        String uploadId,
        int total,
        int completed,
        State status
) {
}
