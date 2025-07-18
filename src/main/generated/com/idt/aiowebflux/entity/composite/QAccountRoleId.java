package com.idt.aiowebflux.entity.composite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountRoleId is a Querydsl query type for AccountRoleId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAccountRoleId extends BeanPath<AccountRoleId> {

    private static final long serialVersionUID = 1262166690L;

    public static final QAccountRoleId accountRoleId = new QAccountRoleId("accountRoleId");

    public final StringPath accountId = createString("accountId");

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    public QAccountRoleId(String variable) {
        super(AccountRoleId.class, forVariable(variable));
    }

    public QAccountRoleId(Path<? extends AccountRoleId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountRoleId(PathMetadata metadata) {
        super(AccountRoleId.class, metadata);
    }

}

