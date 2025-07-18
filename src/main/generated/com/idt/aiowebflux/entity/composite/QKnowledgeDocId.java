package com.idt.aiowebflux.entity.composite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKnowledgeDocId is a Querydsl query type for KnowledgeDocId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QKnowledgeDocId extends BeanPath<KnowledgeDocId> {

    private static final long serialVersionUID = 713139505L;

    public static final QKnowledgeDocId knowledgeDocId = new QKnowledgeDocId("knowledgeDocId");

    public final StringPath docId = createString("docId");

    public final NumberPath<Long> knowledgeId = createNumber("knowledgeId", Long.class);

    public QKnowledgeDocId(String variable) {
        super(KnowledgeDocId.class, forVariable(variable));
    }

    public QKnowledgeDocId(Path<? extends KnowledgeDocId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKnowledgeDocId(PathMetadata metadata) {
        super(KnowledgeDocId.class, metadata);
    }

}

