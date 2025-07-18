package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAclEntry is a Querydsl query type for AclEntry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAclEntry extends EntityPathBase<AclEntry> {

    private static final long serialVersionUID = 1993531485L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAclEntry aclEntry = new QAclEntry("aclEntry");

    public final com.idt.aiowebflux.entity.composite.QAclEntryId id;

    public final NumberPath<Integer> permMask = createNumber("permMask", Integer.class);

    public QAclEntry(String variable) {
        this(AclEntry.class, forVariable(variable), INITS);
    }

    public QAclEntry(Path<? extends AclEntry> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAclEntry(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAclEntry(PathMetadata metadata, PathInits inits) {
        this(AclEntry.class, metadata, inits);
    }

    public QAclEntry(Class<? extends AclEntry> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new com.idt.aiowebflux.entity.composite.QAclEntryId(forProperty("id")) : null;
    }

}

