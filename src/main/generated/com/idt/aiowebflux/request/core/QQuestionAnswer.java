package com.idt.aiowebflux.request.core;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.request.core.QQuestionAnswer is a Querydsl Projection type for QuestionAnswer
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QQuestionAnswer extends ConstructorExpression<QuestionAnswer> {

    private static final long serialVersionUID = -1080624834L;

    public QQuestionAnswer(com.querydsl.core.types.Expression<String> question, com.querydsl.core.types.Expression<String> answer) {
        super(QuestionAnswer.class, new Class<?>[]{String.class, String.class}, question, answer);
    }

}

