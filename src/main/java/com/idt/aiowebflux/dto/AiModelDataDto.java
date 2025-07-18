package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.Feature;
import com.querydsl.core.annotations.QueryProjection;

public record AiModelDataDto(
        Long aiModelId,
        String name,
        Feature feature
) {
    @QueryProjection
    public AiModelDataDto {
    }
}
