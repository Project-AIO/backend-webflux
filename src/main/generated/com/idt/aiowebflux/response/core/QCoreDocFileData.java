package com.idt.aiowebflux.response.core;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.core.QCoreDocFileData is a Querydsl Projection type for CoreDocFileData
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCoreDocFileData extends ConstructorExpression<CoreDocFileData> {

    private static final long serialVersionUID = 67661223L;

    public QCoreDocFileData(com.querydsl.core.types.Expression<String> docId, com.querydsl.core.types.Expression<String> extension, com.querydsl.core.types.Expression<String> path, com.querydsl.core.types.Expression<String> fileName, com.querydsl.core.types.Expression<Integer> totalPage, com.querydsl.core.types.Expression<String> revision) {
        super(CoreDocFileData.class, new Class<?>[]{String.class, String.class, String.class, String.class, int.class, String.class}, docId, extension, path, fileName, totalPage, revision);
    }

}

