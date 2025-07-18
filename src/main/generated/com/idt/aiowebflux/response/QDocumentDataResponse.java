package com.idt.aiowebflux.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.QDocumentDataResponse is a Querydsl Projection type for DocumentDataResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDocumentDataResponse extends ConstructorExpression<DocumentDataResponse> {

    private static final long serialVersionUID = 2133930365L;

    public QDocumentDataResponse(com.querydsl.core.types.Expression<String> docId, com.querydsl.core.types.Expression<Long> folderId, com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.State> state, com.querydsl.core.types.Expression<Integer> progress, com.querydsl.core.types.Expression<DocumentFileData> fileData, com.querydsl.core.types.Expression<java.time.LocalDateTime> uploadDt) {
        super(DocumentDataResponse.class, new Class<?>[]{String.class, long.class, com.idt.aiowebflux.entity.constant.State.class, int.class, DocumentFileData.class, java.time.LocalDateTime.class}, docId, folderId, state, progress, fileData, uploadDt);
    }

}

