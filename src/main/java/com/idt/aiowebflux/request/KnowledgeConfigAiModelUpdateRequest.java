package com.idt.aiowebflux.request;

public record KnowledgeConfigAiModelUpdateRequest(
        Float temperature,
        Float topP,
        Integer topK,
        Integer useMaxToken,
        String apiKey,
        Float overlapTokenR,
        Float scoreTh,
        Integer retrievalCnt,
        Integer resultCnt,
        Float retrievalWeightR

) {
}
