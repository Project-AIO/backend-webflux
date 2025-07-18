package com.idt.aiowebflux.dto;

import com.querydsl.core.annotations.QueryProjection;

public record PageData(
        Long answerId,
        Long similarityDocId,
        Integer page,
        Float leftX,
        Float rightX,
        Float topY,
        Float bottomY
) {
    @QueryProjection
    public PageData {
    }
}
