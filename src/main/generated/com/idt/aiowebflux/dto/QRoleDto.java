package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QRoleDto is a Querydsl Projection type for RoleDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRoleDto extends ConstructorExpression<RoleDto> {

    private static final long serialVersionUID = 1046756836L;

    public QRoleDto(com.querydsl.core.types.Expression<Long> roleId, com.querydsl.core.types.Expression<String> roleName) {
        super(RoleDto.class, new Class<?>[]{long.class, String.class}, roleId, roleName);
    }

}

