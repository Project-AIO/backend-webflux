package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKnowledgeConfig is a Querydsl query type for KnowledgeConfig
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKnowledgeConfig extends EntityPathBase<KnowledgeConfig> {

    private static final long serialVersionUID = 1411591531L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKnowledgeConfig knowledgeConfig = new QKnowledgeConfig("knowledgeConfig");

    public final QKnowledge knowledge;

    public final NumberPath<Long> knowledgeConfigId = createNumber("knowledgeConfigId", Long.class);

    public final NumberPath<Float> overlapTokenR = createNumber("overlapTokenR", Float.class);

    public final NumberPath<Integer> resultCnt = createNumber("resultCnt", Integer.class);

    public final NumberPath<Integer> retrievalCnt = createNumber("retrievalCnt", Integer.class);

    public final NumberPath<Float> retrievalWeightR = createNumber("retrievalWeightR", Float.class);

    public final NumberPath<Float> scoreTh = createNumber("scoreTh", Float.class);

    public QKnowledgeConfig(String variable) {
        this(KnowledgeConfig.class, forVariable(variable), INITS);
    }

    public QKnowledgeConfig(Path<? extends KnowledgeConfig> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKnowledgeConfig(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKnowledgeConfig(PathMetadata metadata, PathInits inits) {
        this(KnowledgeConfig.class, metadata, inits);
    }

    public QKnowledgeConfig(Class<? extends KnowledgeConfig> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.knowledge = inits.isInitialized("knowledge") ? new QKnowledge(forProperty("knowledge"), inits.get("knowledge")) : null;
    }

}

