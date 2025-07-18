package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QPermissionDto is a Querydsl Projection type for PermissionDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPermissionDto extends ConstructorExpression<PermissionDto> {

    private static final long serialVersionUID = 1700289963L;

    public QPermissionDto(com.querydsl.core.types.Expression<Long> permissionId, com.querydsl.core.types.Expression<String> displayName, com.querydsl.core.types.Expression<String> description) {
        super(PermissionDto.class, new Class<?>[]{long.class, String.class, String.class}, permissionId, displayName, description);
    }

}

