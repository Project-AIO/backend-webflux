package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QAccountRoleDto is a Querydsl Projection type for AccountRoleDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAccountRoleDto extends ConstructorExpression<AccountRoleDto> {

    private static final long serialVersionUID = 407783905L;

    public QAccountRoleDto(com.querydsl.core.types.Expression<String> accountId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.Status> status, com.querydsl.core.types.Expression<? extends java.util.List<RoleDto>> roles, com.querydsl.core.types.Expression<java.time.LocalDateTime> updateDt) {
        super(AccountRoleDto.class, new Class<?>[]{String.class, String.class, com.idt.aiowebflux.entity.constant.Status.class, java.util.List.class, java.time.LocalDateTime.class}, accountId, name, status, roles, updateDt);
    }

}

