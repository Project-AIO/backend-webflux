package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPermission is a Querydsl query type for Permission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPermission extends EntityPathBase<Permission> {

    private static final long serialVersionUID = 823983844L;

    public static final QPermission permission = new QPermission("permission");

    public final StringPath code = createString("code");

    public final StringPath description = createString("description");

    public final StringPath displayName = createString("displayName");

    public final NumberPath<Long> permissionId = createNumber("permissionId", Long.class);

    public final EnumPath<com.idt.aiowebflux.entity.constant.PermissionScope> scope = createEnum("scope", com.idt.aiowebflux.entity.constant.PermissionScope.class);

    public QPermission(String variable) {
        super(Permission.class, forVariable(variable));
    }

    public QPermission(Path<? extends Permission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPermission(PathMetadata metadata) {
        super(Permission.class, metadata);
    }

}

