package com.idt.aiowebflux.request;

public record KnowledgeAiModelUpdateRequest(
        Float temperature,
        Float topP,
        Integer topK,
        Integer useMaxToken,
        String apiKey
) {

}
