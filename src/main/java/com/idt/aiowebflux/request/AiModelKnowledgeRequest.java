package com.idt.aiowebflux.request;

public record AiModelKnowledgeRequest(
        Long aiModelId,
        Integer useMaxToken
) {
}
