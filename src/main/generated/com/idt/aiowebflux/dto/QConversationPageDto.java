package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QConversationPageDto is a Querydsl Projection type for ConversationPageDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QConversationPageDto extends ConstructorExpression<ConversationPageDto> {

    private static final long serialVersionUID = 1007942312L;

    public QConversationPageDto(com.querydsl.core.types.Expression<Long> questionId, com.querydsl.core.types.Expression<String> questionMessage, com.querydsl.core.types.Expression<Long> answerId, com.querydsl.core.types.Expression<String> answerMessage, com.querydsl.core.types.Expression<? extends java.util.List<com.idt.aiowebflux.response.core.Ref>> refer) {
        super(ConversationPageDto.class, new Class<?>[]{long.class, String.class, long.class, String.class, java.util.List.class}, questionId, questionMessage, answerId, answerMessage, refer);
    }

}

