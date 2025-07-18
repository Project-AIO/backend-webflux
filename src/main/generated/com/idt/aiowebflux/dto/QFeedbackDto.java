package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QFeedbackDto is a Querydsl Projection type for FeedbackDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFeedbackDto extends ConstructorExpression<FeedbackDto> {

    private static final long serialVersionUID = -347398411L;

    public QFeedbackDto(com.querydsl.core.types.Expression<String> accountId, com.querydsl.core.types.Expression<Long> questionId, com.querydsl.core.types.Expression<String> questionMessage, com.querydsl.core.types.Expression<Long> answerId, com.querydsl.core.types.Expression<String> answerMessage, com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.FeedbackType> feedbackType, com.querydsl.core.types.Expression<String> feedbackComment, com.querydsl.core.types.Expression<String> documentName, com.querydsl.core.types.Expression<Float> similarityDocScore) {
        super(FeedbackDto.class, new Class<?>[]{String.class, long.class, String.class, long.class, String.class, com.idt.aiowebflux.entity.constant.FeedbackType.class, String.class, String.class, float.class}, accountId, questionId, questionMessage, answerId, answerMessage, feedbackType, feedbackComment, documentName, similarityDocScore);
    }

}

