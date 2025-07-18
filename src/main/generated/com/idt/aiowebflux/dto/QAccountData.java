package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QAccountData is a Querydsl Projection type for AccountData
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAccountData extends ConstructorExpression<AccountData> {

    private static final long serialVersionUID = 1820882514L;

    public QAccountData(com.querydsl.core.types.Expression<String> accountId, com.querydsl.core.types.Expression<String> adminId, com.querydsl.core.types.Expression<com.idt.aiowebflux.entity.constant.Role> role, com.querydsl.core.types.Expression<String> name) {
        super(AccountData.class, new Class<?>[]{String.class, String.class, com.idt.aiowebflux.entity.constant.Role.class, String.class}, accountId, adminId, role, name);
    }

}

