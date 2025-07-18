package com.idt.aiowebflux.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.QKnowledgeConfigResponse is a Querydsl Projection type for KnowledgeConfigResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QKnowledgeConfigResponse extends ConstructorExpression<KnowledgeConfigResponse> {

    private static final long serialVersionUID = 533711786L;

    public QKnowledgeConfigResponse(com.querydsl.core.types.Expression<Long> knowledgeCfgId, com.querydsl.core.types.Expression<Long> knowledgeId, com.querydsl.core.types.Expression<Float> overlapTokenR, com.querydsl.core.types.Expression<Integer> resultCnt, com.querydsl.core.types.Expression<Float> scoreTh, com.querydsl.core.types.Expression<Integer> retrievalCnt, com.querydsl.core.types.Expression<Float> retrievalWeightR) {
        super(KnowledgeConfigResponse.class, new Class<?>[]{long.class, long.class, float.class, int.class, float.class, int.class, float.class}, knowledgeCfgId, knowledgeId, overlapTokenR, resultCnt, scoreTh, retrievalCnt, retrievalWeightR);
    }

}

