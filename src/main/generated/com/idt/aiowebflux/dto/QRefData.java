package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QRefData is a Querydsl Projection type for RefData
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRefData extends ConstructorExpression<RefData> {

    private static final long serialVersionUID = 753968952L;

    public QRefData(com.querydsl.core.types.Expression<Long> answerId, com.querydsl.core.types.Expression<String> docId, com.querydsl.core.types.Expression<Float> score, com.querydsl.core.types.Expression<String> extension, com.querydsl.core.types.Expression<String> path, com.querydsl.core.types.Expression<String> fileName, com.querydsl.core.types.Expression<Integer> totalPage, com.querydsl.core.types.Expression<String> revision, com.querydsl.core.types.Expression<Long> similarityDocId) {
        super(RefData.class, new Class<?>[]{long.class, String.class, float.class, String.class, String.class, String.class, int.class, String.class, long.class}, answerId, docId, score, extension, path, fileName, totalPage, revision, similarityDocId);
    }

}

