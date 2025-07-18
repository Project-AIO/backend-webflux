package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHomonym is a Querydsl query type for Homonym
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHomonym extends EntityPathBase<Homonym> {

    private static final long serialVersionUID = -1274639996L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHomonym homonym = new QHomonym("homonym");

    public final NumberPath<Long> homonymId = createNumber("homonymId", Long.class);

    public final QKnowledge knowledge;

    public final StringPath match = createString("match");

    public final StringPath source = createString("source");

    public QHomonym(String variable) {
        this(Homonym.class, forVariable(variable), INITS);
    }

    public QHomonym(Path<? extends Homonym> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHomonym(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHomonym(PathMetadata metadata, PathInits inits) {
        this(Homonym.class, metadata, inits);
    }

    public QHomonym(Class<? extends Homonym> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.knowledge = inits.isInitialized("knowledge") ? new QKnowledge(forProperty("knowledge"), inits.get("knowledge")) : null;
    }

}

