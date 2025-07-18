package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QConversationData is a Querydsl Projection type for ConversationData
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QConversationData extends ConstructorExpression<ConversationData> {

    private static final long serialVersionUID = -2120486702L;

    public QConversationData(com.querydsl.core.types.Expression<Long> questionId, com.querydsl.core.types.Expression<String> questionMessage, com.querydsl.core.types.Expression<Long> answerId, com.querydsl.core.types.Expression<String> answerMessage) {
        super(ConversationData.class, new Class<?>[]{long.class, String.class, long.class, String.class}, questionId, questionMessage, answerId, answerMessage);
    }

}

