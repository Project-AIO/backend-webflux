package com.idt.aiowebflux.entity.composite;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAclEntryId is a Querydsl query type for AclEntryId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAclEntryId extends BeanPath<AclEntryId> {

    private static final long serialVersionUID = -1129186849L;

    public static final QAclEntryId aclEntryId = new QAclEntryId("aclEntryId");

    public final NumberPath<Long> principalId = createNumber("principalId", Long.class);

    public final EnumPath<com.idt.aiowebflux.entity.constant.PrincipalType> principalType = createEnum("principalType", com.idt.aiowebflux.entity.constant.PrincipalType.class);

    public final NumberPath<Long> resourceId = createNumber("resourceId", Long.class);

    public final EnumPath<com.idt.aiowebflux.entity.constant.ResourceType> resourceType = createEnum("resourceType", com.idt.aiowebflux.entity.constant.ResourceType.class);

    public QAclEntryId(String variable) {
        super(AclEntryId.class, forVariable(variable));
    }

    public QAclEntryId(Path<? extends AclEntryId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAclEntryId(PathMetadata metadata) {
        super(AclEntryId.class, metadata);
    }

}

