package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSimilarityDocPage is a Querydsl query type for SimilarityDocPage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSimilarityDocPage extends EntityPathBase<SimilarityDocPage> {

    private static final long serialVersionUID = -1907457841L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSimilarityDocPage similarityDocPage = new QSimilarityDocPage("similarityDocPage");

    public final NumberPath<Float> bottomY = createNumber("bottomY", Float.class);

    public final NumberPath<Float> leftX = createNumber("leftX", Float.class);

    public final NumberPath<Integer> page = createNumber("page", Integer.class);

    public final NumberPath<Float> rightX = createNumber("rightX", Float.class);

    public final QSimilarityDoc similarityDoc;

    public final NumberPath<Long> similarityDocPageId = createNumber("similarityDocPageId", Long.class);

    public final NumberPath<Float> topY = createNumber("topY", Float.class);

    public QSimilarityDocPage(String variable) {
        this(SimilarityDocPage.class, forVariable(variable), INITS);
    }

    public QSimilarityDocPage(Path<? extends SimilarityDocPage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSimilarityDocPage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSimilarityDocPage(PathMetadata metadata, PathInits inits) {
        this(SimilarityDocPage.class, metadata, inits);
    }

    public QSimilarityDocPage(Class<? extends SimilarityDocPage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.similarityDoc = inits.isInitialized("similarityDoc") ? new QSimilarityDoc(forProperty("similarityDoc"), inits.get("similarityDoc")) : null;
    }

}

