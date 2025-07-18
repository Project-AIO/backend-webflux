package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QModelDataDto is a Querydsl Projection type for ModelDataDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QModelDataDto extends ConstructorExpression<ModelDataDto> {

    private static final long serialVersionUID = 850847473L;

    public QModelDataDto(com.querydsl.core.types.Expression<String> apiKey, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> baseUrl, com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.Feature> feature) {
        super(ModelDataDto.class, new Class<?>[]{String.class, String.class, String.class, com.idt.aiowebflux.entity.constant.Feature.class}, apiKey, name, baseUrl, feature);
    }

}

