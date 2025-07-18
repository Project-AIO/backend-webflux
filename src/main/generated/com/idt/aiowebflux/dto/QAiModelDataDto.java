package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QAiModelDataDto is a Querydsl Projection type for AiModelDataDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAiModelDataDto extends ConstructorExpression<AiModelDataDto> {

    private static final long serialVersionUID = 1539194361L;

    public QAiModelDataDto(com.querydsl.core.types.Expression<Long> aiModelId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.Feature> feature) {
        super(AiModelDataDto.class, new Class<?>[]{long.class, String.class, com.idt.aiowebflux.entity.constant.Feature.class}, aiModelId, name, feature);
    }

}

