package com.idt.aiowebflux.entity.composite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProjectAccountId is a Querydsl query type for ProjectAccountId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProjectAccountId extends BeanPath<ProjectAccountId> {

    private static final long serialVersionUID = -1086061397L;

    public static final QProjectAccountId projectAccountId = new QProjectAccountId("projectAccountId");

    public final StringPath accountId = createString("accountId");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public QProjectAccountId(String variable) {
        super(ProjectAccountId.class, forVariable(variable));
    }

    public QProjectAccountId(Path<? extends ProjectAccountId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProjectAccountId(PathMetadata metadata) {
        super(ProjectAccountId.class, metadata);
    }

}

