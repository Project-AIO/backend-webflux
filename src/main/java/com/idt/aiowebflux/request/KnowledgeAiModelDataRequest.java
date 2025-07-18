package com.idt.aiowebflux.request;

import com.idt.aiowebflux.entity.constant.Feature;

public record KnowledgeAiModelDataRequest(
        Feature feature,
        String name,
        String apiKey,
        Integer useMaxToken,
        String baseUrl,
        Float topP,
        Integer topK,
        Float temperature
) {

}
