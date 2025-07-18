package com.idt.aiowebflux.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.QAiModelNameResponse is a Querydsl Projection type for AiModelNameResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAiModelNameResponse extends ConstructorExpression<AiModelNameResponse> {

    private static final long serialVersionUID = 625158358L;

    public QAiModelNameResponse(com.querydsl.core.types.Expression<String> modelName, com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.Feature> feature) {
        super(AiModelNameResponse.class, new Class<?>[]{String.class, com.idt.aiowebflux.entity.constant.Feature.class}, modelName, feature);
    }

}

