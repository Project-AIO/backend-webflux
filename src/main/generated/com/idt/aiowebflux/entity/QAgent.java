package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgent is a Querydsl query type for Agent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgent extends EntityPathBase<Agent> {

    private static final long serialVersionUID = 2061234384L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgent agent = new QAgent("agent");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAccount account;

    public final NumberPath<Long> agentId = createNumber("agentId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final StringPath description = createString("description");

    public final StringPath dsl = createString("dsl");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updateDt = createDateTime("updateDt", java.time.LocalDateTime.class);

    public QAgent(String variable) {
        this(Agent.class, forVariable(variable), INITS);
    }

    public QAgent(Path<? extends Agent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgent(PathMetadata metadata, PathInits inits) {
        this(Agent.class, metadata, inits);
    }

    public QAgent(Class<? extends Agent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
    }

}

