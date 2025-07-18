package com.idt.aiowebflux.request;

public record LoginRequest(
        String accountId,
        String pw
) {
}
