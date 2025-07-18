package com.idt.aiowebflux.response.core;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.core.QPages is a Querydsl Projection type for Pages
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPages extends ConstructorExpression<Pages> {

    private static final long serialVersionUID = 1544270828L;

    public QPages(com.querydsl.core.types.Expression<Integer> page, com.querydsl.core.types.Expression<Float> leftX, com.querydsl.core.types.Expression<Float> rightX, com.querydsl.core.types.Expression<Float> topY, com.querydsl.core.types.Expression<Float> bottomY) {
        super(Pages.class, new Class<?>[]{int.class, float.class, float.class, float.class, float.class}, page, leftX, rightX, topY, bottomY);
    }

}

