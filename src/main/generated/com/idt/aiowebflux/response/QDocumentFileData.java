package com.idt.aiowebflux.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.QDocumentFileData is a Querydsl Projection type for DocumentFileData
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDocumentFileData extends ConstructorExpression<DocumentFileData> {

    private static final long serialVersionUID = 644274104L;

    public QDocumentFileData(com.querydsl.core.types.Expression<Long> docFileId, com.querydsl.core.types.Expression<String> extension, com.querydsl.core.types.Expression<String> path, com.querydsl.core.types.Expression<String> fileName, com.querydsl.core.types.Expression<Integer> totalPage, com.querydsl.core.types.Expression<String> revision) {
        super(DocumentFileData.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class, String.class}, docFileId, extension, path, fileName, totalPage, revision);
    }

}

