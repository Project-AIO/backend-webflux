package com.idt.aiowebflux.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.QDocumentDatasetResponse is a Querydsl Projection type for DocumentDatasetResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDocumentDatasetResponse extends ConstructorExpression<DocumentDatasetResponse> {

    private static final long serialVersionUID = 242226215L;

    public QDocumentDatasetResponse(com.querydsl.core.types.Expression<String> docId, com.querydsl.core.types.Expression<String> sourceName, com.querydsl.core.types.Expression<String> fileName, com.querydsl.core.types.Expression<Integer> totalPages, com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.State> state, com.querydsl.core.types.Expression<Integer> progress, com.querydsl.core.types.Expression<java.time.LocalDateTime> uploadDate, com.querydsl.core.types.Expression<Long> fileByteSize) {
        super(DocumentDatasetResponse.class, new Class<?>[]{String.class, String.class, String.class, int.class, com.idt.aiowebflux.entity.constant.State.class, int.class, java.time.LocalDateTime.class, long.class}, docId, sourceName, fileName, totalPages, state, progress, uploadDate, fileByteSize);
    }

}

