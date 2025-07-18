package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.Feature;
import com.querydsl.core.annotations.QueryProjection;

public record ModelDataDto(
        String apiKey,
        String name,
        String baseUrl,
        Feature feature
) {
    @QueryProjection
    public ModelDataDto {

    }
}
