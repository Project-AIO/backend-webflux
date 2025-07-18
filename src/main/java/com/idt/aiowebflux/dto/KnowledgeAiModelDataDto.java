package com.idt.aiowebflux.dto;

import com.querydsl.core.annotations.QueryProjection;

public record KnowledgeAiModelDataDto(
        Long knowledgeId,
        Long aiModelId,
//        Feature feature,
//        String roleName,
//        String apiKey,
        Integer useMaxToken
//        String baseUrl,
//        Float topP,
//        Integer topK,
//        Float temperature
) {
    @QueryProjection
    public KnowledgeAiModelDataDto {

    }
}
