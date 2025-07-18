package com.idt.aiowebflux.entity.composite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRolePermissionId is a Querydsl query type for RolePermissionId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRolePermissionId extends BeanPath<RolePermissionId> {

    private static final long serialVersionUID = 99185276L;

    public static final QRolePermissionId rolePermissionId = new QRolePermissionId("rolePermissionId");

    public final NumberPath<Long> permissionId = createNumber("permissionId", Long.class);

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public QRolePermissionId(String variable) {
        super(RolePermissionId.class, forVariable(variable));
    }

    public QRolePermissionId(Path<? extends RolePermissionId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRolePermissionId(PathMetadata metadata) {
        super(RolePermissionId.class, metadata);
    }

}

