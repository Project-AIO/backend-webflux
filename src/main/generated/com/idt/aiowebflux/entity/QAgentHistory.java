package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgentHistory is a Querydsl query type for AgentHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgentHistory extends EntityPathBase<AgentHistory> {

    private static final long serialVersionUID = -1002241628L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgentHistory agentHistory = new QAgentHistory("agentHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAccount account;

    public final QAgent agent;

    public final NumberPath<Long> agentHistoryId = createNumber("agentHistoryId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final StringPath description = createString("description");

    public final StringPath dsl = createString("dsl");

    public QAgentHistory(String variable) {
        this(AgentHistory.class, forVariable(variable), INITS);
    }

    public QAgentHistory(Path<? extends AgentHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgentHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgentHistory(PathMetadata metadata, PathInits inits) {
        this(AgentHistory.class, metadata, inits);
    }

    public QAgentHistory(Class<? extends AgentHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
        this.agent = inits.isInitialized("agent") ? new QAgent(forProperty("agent"), inits.get("agent")) : null;
    }

}

