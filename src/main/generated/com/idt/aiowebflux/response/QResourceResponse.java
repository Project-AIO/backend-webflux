package com.idt.aiowebflux.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.response.QResourceResponse is a Querydsl Projection type for ResourceResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResourceResponse extends ConstructorExpression<ResourceResponse> {

    private static final long serialVersionUID = 909050022L;

    public QResourceResponse(com.querydsl.core.types.Expression<Long> resourceId, com.querydsl.core.types.Expression<String> resourceName) {
        super(ResourceResponse.class, new Class<?>[]{long.class, String.class}, resourceId, resourceName);
    }

}

