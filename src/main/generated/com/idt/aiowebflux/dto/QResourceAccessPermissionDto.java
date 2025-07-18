package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QResourceAccessPermissionDto is a Querydsl Projection type for ResourceAccessPermissionDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResourceAccessPermissionDto extends ConstructorExpression<ResourceAccessPermissionDto> {

    private static final long serialVersionUID = 1065823353L;

    public QResourceAccessPermissionDto(com.querydsl.core.types.Expression<Long> resourceId, com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.ResourceType> resourceType, com.querydsl.core.types.Expression<String> resourceName, com.querydsl.core.types.Expression<Integer> permMask) {
        super(ResourceAccessPermissionDto.class, new Class<?>[]{long.class, com.idt.aiowebflux.entity.constant.ResourceType.class, String.class, int.class}, resourceId, resourceType, resourceName, permMask);
    }

}

