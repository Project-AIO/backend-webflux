package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.KnowledgeAiModel;
import com.querydsl.core.annotations.QueryProjection;

import java.util.List;

public record KnowledgeAiModelDataResponse(
        Long knowledgeId,
        Long aiModelId,
//        String apiKey,
//        Float temperature,
//        Float topP,
        Integer useMaxToken,
//        Integer topK,
        AiModelNameResponse aiModelNameResponse
) {
    @QueryProjection
    public KnowledgeAiModelDataResponse {
    }

    public static List<KnowledgeAiModelDataResponse> from(final List<KnowledgeAiModel> entities) {
        return entities.stream()
                .map(KnowledgeAiModelDataResponse::from)
                .toList();
    }

    public static KnowledgeAiModelDataResponse from(final KnowledgeAiModel entity) {
        return new KnowledgeAiModelDataResponse(
                entity.getKnowledge().getKnowledgeId(),
                entity.getAiModel().getAiModelId(),
//                entity.getApiKey(),
//                entity.getTemperature(),
//                entity.getTopP(),
                entity.getUseMaxToken(),
//                entity.getTopK(),
                new AiModelNameResponse(
                        entity.getAiModel().getName(),
                        entity.getAiModel().getFeature()
                )
        );
    }
}
