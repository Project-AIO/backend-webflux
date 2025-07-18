package com.idt.aiowebflux.response.sso;

public record SsoResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        String expiresIn
) {
}
