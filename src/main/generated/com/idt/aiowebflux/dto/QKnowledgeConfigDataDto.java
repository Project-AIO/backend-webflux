package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QKnowledgeConfigDataDto is a Querydsl Projection type for KnowledgeConfigDataDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QKnowledgeConfigDataDto extends ConstructorExpression<KnowledgeConfigDataDto> {

    private static final long serialVersionUID = -1113611622L;

    public QKnowledgeConfigDataDto(com.querydsl.core.types.Expression<Integer> resultCnt, com.querydsl.core.types.Expression<Float> scoreTh, com.querydsl.core.types.Expression<Integer> retrievalCnt, com.querydsl.core.types.Expression<Float> retrievalWeightR) {
        super(KnowledgeConfigDataDto.class, new Class<?>[]{int.class, float.class, int.class, float.class}, resultCnt, scoreTh, retrievalCnt, retrievalWeightR);
    }

}

