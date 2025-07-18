package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKnowledgeDoc is a Querydsl query type for KnowledgeDoc
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKnowledgeDoc extends EntityPathBase<KnowledgeDoc> {

    private static final long serialVersionUID = -1702022225L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKnowledgeDoc knowledgeDoc = new QKnowledgeDoc("knowledgeDoc");

    public final QDocument doc;

    public final com.idt.aiowebflux.entity.composite.QKnowledgeDocId id;

    public final QKnowledge knowledge;

    public final NumberPath<Integer> progress = createNumber("progress", Integer.class);

    public final EnumPath<com.idt.aiowebflux.entity.constant.State> state = createEnum("state", com.idt.aiowebflux.entity.constant.State.class);

    public QKnowledgeDoc(String variable) {
        this(KnowledgeDoc.class, forVariable(variable), INITS);
    }

    public QKnowledgeDoc(Path<? extends KnowledgeDoc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKnowledgeDoc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKnowledgeDoc(PathMetadata metadata, PathInits inits) {
        this(KnowledgeDoc.class, metadata, inits);
    }

    public QKnowledgeDoc(Class<? extends KnowledgeDoc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.doc = inits.isInitialized("doc") ? new QDocument(forProperty("doc"), inits.get("doc")) : null;
        this.id = inits.isInitialized("id") ? new com.idt.aiowebflux.entity.composite.QKnowledgeDocId(forProperty("id")) : null;
        this.knowledge = inits.isInitialized("knowledge") ? new QKnowledge(forProperty("knowledge"), inits.get("knowledge")) : null;
    }

}

