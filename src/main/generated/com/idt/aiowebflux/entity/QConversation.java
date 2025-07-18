package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConversation is a Querydsl query type for Conversation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConversation extends EntityPathBase<Conversation> {

    private static final long serialVersionUID = 1529554008L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConversation conversation = new QConversation("conversation");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAccount account;

    public final NumberPath<Long> conversationId = createNumber("conversationId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDt = _super.createDt;

    public final QKnowledge knowledge;

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> usedDt = createDateTime("usedDt", java.time.LocalDateTime.class);

    public QConversation(String variable) {
        this(Conversation.class, forVariable(variable), INITS);
    }

    public QConversation(Path<? extends Conversation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConversation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConversation(PathMetadata metadata, PathInits inits) {
        this(Conversation.class, metadata, inits);
    }

    public QConversation(Class<? extends Conversation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
        this.knowledge = inits.isInitialized("knowledge") ? new QKnowledge(forProperty("knowledge"), inits.get("knowledge")) : null;
    }

}

