package com.idt.aiowebflux.request;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.request.QDocumentFileDataRequest is a Querydsl Projection type for DocumentFileDataRequest
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDocumentFileDataRequest extends ConstructorExpression<DocumentFileDataRequest> {

    private static final long serialVersionUID = 436985817L;

    public QDocumentFileDataRequest(com.querydsl.core.types.Expression<String> docId, com.querydsl.core.types.Expression<String> fileName, com.querydsl.core.types.Expression<String> extension, com.querydsl.core.types.Expression<String> revision) {
        super(DocumentFileDataRequest.class, new Class<?>[]{String.class, String.class, String.class, String.class}, docId, fileName, extension, revision);
    }

}

