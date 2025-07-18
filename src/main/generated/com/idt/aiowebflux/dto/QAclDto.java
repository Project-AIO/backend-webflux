package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QAclDto is a Querydsl Projection type for AclDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAclDto extends ConstructorExpression<AclDto> {

    private static final long serialVersionUID = 644336954L;

    public QAclDto(com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.PrincipalType> principalType, com.querydsl.core.types.Expression<Long> principalId, com.querydsl.core.types.Expression<String> principalName, com.querydsl.core.types.Expression<? extends java.util.Map<com.idt.aiowebflux.entity.constant.ResourceType, java.util.List<ResourceAccessPermissionDto>>> resourceIdToNameMap) {
        super(AclDto.class, new Class<?>[]{com.idt.aiowebflux.entity.constant.PrincipalType.class, long.class, String.class, java.util.Map.class}, principalType, principalId, principalName, resourceIdToNameMap);
    }

}

