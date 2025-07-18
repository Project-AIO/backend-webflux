package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.KnowledgeConfig;
import com.querydsl.core.annotations.QueryProjection;

public record KnowledgeConfigResponse(
        Long knowledgeCfgId,
        Long knowledgeId,
        Float overlapTokenR,
        Integer resultCnt,
        Float scoreTh,
        Integer retrievalCnt,
        Float retrievalWeightR
) {
    @QueryProjection
    public KnowledgeConfigResponse {

    }

    static public KnowledgeConfigResponse from(final KnowledgeConfig entity) {
        return new KnowledgeConfigResponse(
                entity.getKnowledgeConfigId(),
                entity.getKnowledge().getKnowledgeId(),
                entity.getOverlapTokenR(),
                entity.getResultCnt(),
                entity.getScoreTh(),
                entity.getRetrievalCnt(),
                entity.getRetrievalWeightR()
        );

    }
}
