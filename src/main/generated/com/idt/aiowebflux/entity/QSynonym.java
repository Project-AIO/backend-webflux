package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSynonym is a Querydsl query type for Synonym
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSynonym extends EntityPathBase<Synonym> {

    private static final long serialVersionUID = 185180934L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSynonym synonym = new QSynonym("synonym");

    public final QKnowledge knowledge;

    public final StringPath match = createString("match");

    public final StringPath source = createString("source");

    public final NumberPath<Long> synonymId = createNumber("synonymId", Long.class);

    public QSynonym(String variable) {
        this(Synonym.class, forVariable(variable), INITS);
    }

    public QSynonym(Path<? extends Synonym> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSynonym(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSynonym(PathMetadata metadata, PathInits inits) {
        this(Synonym.class, metadata, inits);
    }

    public QSynonym(Class<? extends Synonym> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.knowledge = inits.isInitialized("knowledge") ? new QKnowledge(forProperty("knowledge"), inits.get("knowledge")) : null;
    }

}

