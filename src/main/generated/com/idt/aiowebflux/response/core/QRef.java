package com.idt.aiowebflux.response.core;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.core.QRef is a Querydsl Projection type for Ref
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRef extends ConstructorExpression<Ref> {

    private static final long serialVersionUID = -458725701L;

    public QRef(com.querydsl.core.types.Expression<String> docId, com.querydsl.core.types.Expression<? extends java.util.List<Pages>> pages, com.querydsl.core.types.Expression<Float> score, com.querydsl.core.types.Expression<String> extension, com.querydsl.core.types.Expression<String> path, com.querydsl.core.types.Expression<String> fileName, com.querydsl.core.types.Expression<Integer> totalPage, com.querydsl.core.types.Expression<String> revision) {
        super(Ref.class, new Class<?>[]{String.class, java.util.List.class, float.class, String.class, String.class, String.class, int.class, String.class}, docId, pages, score, extension, path, fileName, totalPage, revision);
    }

}

