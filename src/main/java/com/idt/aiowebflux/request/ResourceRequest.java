package com.idt.aiowebflux.request;

public record ResourceRequest(
        Long resourceId,
        Integer permMask
) {
}
