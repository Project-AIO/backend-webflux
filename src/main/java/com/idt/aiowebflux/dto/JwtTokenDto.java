package com.idt.aiowebflux.dto;

public record JwtTokenDto(
        String accessToken,
        String refreshToken
) {

}
