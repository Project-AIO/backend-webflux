package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSimilarityDoc is a Querydsl query type for SimilarityDoc
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSimilarityDoc extends EntityPathBase<SimilarityDoc> {

    private static final long serialVersionUID = -934102496L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSimilarityDoc similarityDoc = new QSimilarityDoc("similarityDoc");

    public final QAnswer answer;

    public final QDocument document;

    public final NumberPath<Float> score = createNumber("score", Float.class);

    public final NumberPath<Long> similarityDocId = createNumber("similarityDocId", Long.class);

    public QSimilarityDoc(String variable) {
        this(SimilarityDoc.class, forVariable(variable), INITS);
    }

    public QSimilarityDoc(Path<? extends SimilarityDoc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSimilarityDoc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSimilarityDoc(PathMetadata metadata, PathInits inits) {
        this(SimilarityDoc.class, metadata, inits);
    }

    public QSimilarityDoc(Class<? extends SimilarityDoc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.answer = inits.isInitialized("answer") ? new QAnswer(forProperty("answer"), inits.get("answer")) : null;
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
    }

}

