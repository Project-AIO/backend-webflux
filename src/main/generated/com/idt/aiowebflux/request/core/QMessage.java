package com.idt.aiowebflux.request.core;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.request.core.QMessage is a Querydsl Projection type for Message
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMessage extends ConstructorExpression<Message> {

    private static final long serialVersionUID = -400437427L;

    public QMessage(com.querydsl.core.types.Expression<String> question, com.querydsl.core.types.Expression<String> answer) {
        super(Message.class, new Class<?>[]{String.class, String.class}, question, answer);
    }

}

