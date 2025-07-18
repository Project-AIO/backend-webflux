package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKnowledgeAiModel is a Querydsl query type for KnowledgeAiModel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKnowledgeAiModel extends EntityPathBase<KnowledgeAiModel> {

    private static final long serialVersionUID = -1167330600L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKnowledgeAiModel knowledgeAiModel = new QKnowledgeAiModel("knowledgeAiModel");

    public final QAiModel aiModel;

    public final com.idt.aiowebflux.entity.composite.QKnowledgeAiModelId id;

    public final QKnowledge knowledge;

    public final NumberPath<Integer> useMaxToken = createNumber("useMaxToken", Integer.class);

    public QKnowledgeAiModel(String variable) {
        this(KnowledgeAiModel.class, forVariable(variable), INITS);
    }

    public QKnowledgeAiModel(Path<? extends KnowledgeAiModel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKnowledgeAiModel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKnowledgeAiModel(PathMetadata metadata, PathInits inits) {
        this(KnowledgeAiModel.class, metadata, inits);
    }

    public QKnowledgeAiModel(Class<? extends KnowledgeAiModel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.aiModel = inits.isInitialized("aiModel") ? new QAiModel(forProperty("aiModel")) : null;
        this.id = inits.isInitialized("id") ? new com.idt.aiowebflux.entity.composite.QKnowledgeAiModelId(forProperty("id")) : null;
        this.knowledge = inits.isInitialized("knowledge") ? new QKnowledge(forProperty("knowledge"), inits.get("knowledge")) : null;
    }

}

