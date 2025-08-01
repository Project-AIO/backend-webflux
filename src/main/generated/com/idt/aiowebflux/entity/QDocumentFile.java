package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocumentFile is a Querydsl query type for DocumentFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocumentFile extends EntityPathBase<DocumentFile> {

    private static final long serialVersionUID = -684229364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocumentFile documentFile = new QDocumentFile("documentFile");

    public final NumberPath<Long> docFileId = createNumber("docFileId", Long.class);

    public final QDocument document;

    public final StringPath extension = createString("extension");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath path = createString("path");

    public final StringPath revision = createString("revision");

    public final NumberPath<Integer> totalPage = createNumber("totalPage", Integer.class);

    public QDocumentFile(String variable) {
        this(DocumentFile.class, forVariable(variable), INITS);
    }

    public QDocumentFile(Path<? extends DocumentFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocumentFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocumentFile(PathMetadata metadata, PathInits inits) {
        this(DocumentFile.class, metadata, inits);
    }

    public QDocumentFile(Class<? extends DocumentFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
    }

}

