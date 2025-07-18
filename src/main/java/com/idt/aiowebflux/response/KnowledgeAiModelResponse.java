package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.KnowledgeAiModel;

import java.util.List;

public record KnowledgeAiModelResponse(
        Long knowledgeId,
        Long aiModelId,
//        String apiKey,
//        Float temperature,
//        Float topP,
        Integer useMaxToken
//        Integer topK
) {
    public static List<KnowledgeAiModelResponse> from(final List<KnowledgeAiModel> entities) {
        return entities.stream()
                .map(KnowledgeAiModelResponse::from)
                .toList();
    }

    public static KnowledgeAiModelResponse from(final KnowledgeAiModel entity) {
        return new KnowledgeAiModelResponse(
                entity.getKnowledge().getKnowledgeId(),
                entity.getAiModel().getAiModelId(),
//                entity.getApiKey(),
//                entity.getTemperature(),
//                entity.getTopP(),
                entity.getUseMaxToken()
//                entity.getTopK()
        );
    }
}
