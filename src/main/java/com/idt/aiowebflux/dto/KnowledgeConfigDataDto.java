package com.idt.aiowebflux.dto;

import com.querydsl.core.annotations.QueryProjection;

public record KnowledgeConfigDataDto(
        Integer resultCnt,
        Float scoreTh,
        Integer retrievalCnt,
        Float retrievalWeightR
) {
    @QueryProjection
    public KnowledgeConfigDataDto {
    }
}
