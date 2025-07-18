package com.idt.aiowebflux.entity.composite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKnowledgeAiModelId is a Querydsl query type for KnowledgeAiModelId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QKnowledgeAiModelId extends BeanPath<KnowledgeAiModelId> {

    private static final long serialVersionUID = -1560026470L;

    public static final QKnowledgeAiModelId knowledgeAiModelId = new QKnowledgeAiModelId("knowledgeAiModelId");

    public final NumberPath<Long> aiModelId = createNumber("aiModelId", Long.class);

    public final NumberPath<Long> knowledgeId = createNumber("knowledgeId", Long.class);

    public QKnowledgeAiModelId(String variable) {
        super(KnowledgeAiModelId.class, forVariable(variable));
    }

    public QKnowledgeAiModelId(Path<? extends KnowledgeAiModelId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKnowledgeAiModelId(PathMetadata metadata) {
        super(KnowledgeAiModelId.class, metadata);
    }

}

