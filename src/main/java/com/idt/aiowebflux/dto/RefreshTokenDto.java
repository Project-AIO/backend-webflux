package com.idt.aiowebflux.dto;

import jakarta.validation.constraints.NotNull;

public record RefreshTokenDto(
        @NotNull String adminId,
        String newAccessToken,
        String newRefreshToken
) {



}
