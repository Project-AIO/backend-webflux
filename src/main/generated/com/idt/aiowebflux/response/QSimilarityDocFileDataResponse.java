package com.idt.aiowebflux.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.QSimilarityDocFileDataResponse is a Querydsl Projection type for SimilarityDocFileDataResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSimilarityDocFileDataResponse extends ConstructorExpression<SimilarityDocFileDataResponse> {

    private static final long serialVersionUID = -554570171L;

    public QSimilarityDocFileDataResponse(com.querydsl.core.types.Expression<Long> similarityDocId, com.querydsl.core.types.Expression<String> docId, com.querydsl.core.types.Expression<Float> score, com.querydsl.core.types.Expression<String> extension, com.querydsl.core.types.Expression<String> path, com.querydsl.core.types.Expression<String> fileName, com.querydsl.core.types.Expression<Integer> totalPage, com.querydsl.core.types.Expression<String> revision) {
        super(SimilarityDocFileDataResponse.class, new Class<?>[]{long.class, String.class, float.class, String.class, String.class, String.class, int.class, String.class}, similarityDocId, docId, score, extension, path, fileName, totalPage, revision);
    }

}

