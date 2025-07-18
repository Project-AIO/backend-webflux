package com.idt.aiowebflux.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.QKnowledgeAiModelDataResponse is a Querydsl Projection type for KnowledgeAiModelDataResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QKnowledgeAiModelDataResponse extends ConstructorExpression<KnowledgeAiModelDataResponse> {

    private static final long serialVersionUID = 363526885L;

    public QKnowledgeAiModelDataResponse(com.querydsl.core.types.Expression<Long> knowledgeId, com.querydsl.core.types.Expression<Long> aiModelId, com.querydsl.core.types.Expression<Integer> useMaxToken, com.querydsl.core.types.Expression<AiModelNameResponse> aiModelNameResponse) {
        super(KnowledgeAiModelDataResponse.class, new Class<?>[]{long.class, long.class, int.class, AiModelNameResponse.class}, knowledgeId, aiModelId, useMaxToken, aiModelNameResponse);
    }

}

