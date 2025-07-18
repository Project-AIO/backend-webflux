package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QRolePermissionDto is a Querydsl Projection type for RolePermissionDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRolePermissionDto extends ConstructorExpression<RolePermissionDto> {

    private static final long serialVersionUID = 528185173L;

    public QRolePermissionDto(com.querydsl.core.types.Expression<Long> roleId, com.querydsl.core.types.Expression<String> roleName, com.querydsl.core.types.Expression<? extends java.util.Set<PermissionDto>> permissions) {
        super(RolePermissionDto.class, new Class<?>[]{long.class, String.class, java.util.Set.class}, roleId, roleName, permissions);
    }

}

