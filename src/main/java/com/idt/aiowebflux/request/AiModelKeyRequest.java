package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AiModelKeyRequest(
        @NotNull
        List<Long> aiModelIds,
        @NotNull
        String apiKey,
        @NotNull
        String baseUrl
) {
}
