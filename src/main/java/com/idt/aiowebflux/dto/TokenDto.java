package com.idt.aiowebflux.dto;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record TokenDto(
        @NotNull
        String accessToken,
        @NonNull
        String refreshToken
) {

}
