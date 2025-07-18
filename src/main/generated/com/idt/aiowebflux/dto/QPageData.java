package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QPageData is a Querydsl Projection type for PageData
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPageData extends ConstructorExpression<PageData> {

    private static final long serialVersionUID = -784243234L;

    public QPageData(com.querydsl.core.types.Expression<Long> answerId, com.querydsl.core.types.Expression<Long> similarityDocId, com.querydsl.core.types.Expression<Integer> page, com.querydsl.core.types.Expression<Float> leftX, com.querydsl.core.types.Expression<Float> rightX, com.querydsl.core.types.Expression<Float> topY, com.querydsl.core.types.Expression<Float> bottomY) {
        super(PageData.class, new Class<?>[]{long.class, long.class, int.class, float.class, float.class, float.class, float.class}, answerId, similarityDocId, page, leftX, rightX, topY, bottomY);
    }

}

