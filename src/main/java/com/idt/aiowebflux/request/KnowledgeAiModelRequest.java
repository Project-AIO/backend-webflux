package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record KnowledgeAiModelRequest(
        @NotNull
        List<Long> aiModelIds,
        Float temperature,
        Float topP,
        Integer topK,
        Integer useMaxToken,
        String apiKey
) {

}
