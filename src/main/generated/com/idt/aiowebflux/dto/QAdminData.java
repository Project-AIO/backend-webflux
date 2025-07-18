package com.idt.aiowebflux.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.idt.aiowebflux.dto.QAdminData is a Querydsl Projection type for AdminData
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAdminData extends ConstructorExpression<AdminData> {

    private static final long serialVersionUID = 621250868L;

    public QAdminData(com.querydsl.core.types.Expression<String> adminId, com.querydsl.core.types.Expression<String> licenseKey) {
        super(AdminData.class, new Class<?>[]{String.class, String.class}, adminId, licenseKey);
    }

}

