package com.idt.aiowebflux.dto;

import java.time.Instant;

public record JwtPrincipal(
        String accountId,
        String jti,
        Instant expiresAt
) {
}
