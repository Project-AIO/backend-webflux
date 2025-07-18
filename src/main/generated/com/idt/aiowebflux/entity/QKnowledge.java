package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKnowledge is a Querydsl query type for Knowledge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKnowledge extends EntityPathBase<Knowledge> {

    private static final long serialVersionUID = -1731970871L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKnowledge knowledge = new QKnowledge("knowledge");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAccount account;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> knowledgeId = createNumber("knowledgeId", Long.class);

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.LocalDateTime> updateDt = createDateTime("updateDt", java.time.LocalDateTime.class);

    public QKnowledge(String variable) {
        this(Knowledge.class, forVariable(variable), INITS);
    }

    public QKnowledge(Path<? extends Knowledge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKnowledge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKnowledge(PathMetadata metadata, PathInits inits) {
        this(Knowledge.class, metadata, inits);
    }

    public QKnowledge(Class<? extends Knowledge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
    }

}

