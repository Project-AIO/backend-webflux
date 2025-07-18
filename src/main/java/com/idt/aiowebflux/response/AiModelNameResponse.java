package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.Feature;
import com.querydsl.core.annotations.QueryProjection;

public record AiModelNameResponse(
        String modelName,
        Feature feature
) {
    @QueryProjection
    public AiModelNameResponse {

    }
}
