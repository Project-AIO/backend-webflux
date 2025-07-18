package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountRole is a Querydsl query type for AccountRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountRole extends EntityPathBase<AccountRole> {

    private static final long serialVersionUID = -493909554L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountRole accountRole = new QAccountRole("accountRole");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAccount account;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final com.idt.aiowebflux.entity.composite.QAccountRoleId id;

    public final QRole role;

    public final DateTimePath<java.time.LocalDateTime> updateDt = createDateTime("updateDt", java.time.LocalDateTime.class);

    public QAccountRole(String variable) {
        this(AccountRole.class, forVariable(variable), INITS);
    }

    public QAccountRole(Path<? extends AccountRole> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountRole(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountRole(PathMetadata metadata, PathInits inits) {
        this(AccountRole.class, metadata, inits);
    }

    public QAccountRole(Class<? extends AccountRole> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
        this.id = inits.isInitialized("id") ? new com.idt.aiowebflux.entity.composite.QAccountRoleId(forProperty("id")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role")) : null;
    }

}

