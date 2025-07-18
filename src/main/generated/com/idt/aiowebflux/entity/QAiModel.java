package com.idt.aiowebflux.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAiModel is a Querydsl query type for AiModel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAiModel extends EntityPathBase<AiModel> {

    private static final long serialVersionUID = 901431020L;

    public static final QAiModel aiModel = new QAiModel("aiModel");

    public final NumberPath<Long> aiModelId = createNumber("aiModelId", Long.class);

    public final StringPath baseUrl = createString("baseUrl");

    public final StringPath description = createString("description");

    public final EnumPath<com.idt.aiowebflux.entity.constant.Feature> feature = createEnum("feature", com.idt.aiowebflux.entity.constant.Feature.class);

    public final NumberPath<Integer> maxToken = createNumber("maxToken", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath provider = createString("provider");

    public QAiModel(String variable) {
        super(AiModel.class, forVariable(variable));
    }

    public QAiModel(Path<? extends AiModel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAiModel(PathMetadata metadata) {
        super(AiModel.class, metadata);
    }

}

