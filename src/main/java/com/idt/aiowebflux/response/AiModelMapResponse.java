package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.Feature;

import java.util.List;
import java.util.Map;

public record AiModelMapResponse(
        Map<Feature, List<AiModelResponse>> aiModelMap
) {
}
