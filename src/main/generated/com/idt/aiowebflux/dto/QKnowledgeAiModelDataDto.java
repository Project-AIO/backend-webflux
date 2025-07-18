package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QKnowledgeAiModelDataDto is a Querydsl Projection type for KnowledgeAiModelDataDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QKnowledgeAiModelDataDto extends ConstructorExpression<KnowledgeAiModelDataDto> {

    private static final long serialVersionUID = 158944557L;

    public QKnowledgeAiModelDataDto(com.querydsl.core.types.Expression<Long> knowledgeId, com.querydsl.core.types.Expression<Long> aiModelId, com.querydsl.core.types.Expression<Integer> useMaxToken) {
        super(KnowledgeAiModelDataDto.class, new Class<?>[]{long.class, long.class, int.class}, knowledgeId, aiModelId, useMaxToken);
    }

}

