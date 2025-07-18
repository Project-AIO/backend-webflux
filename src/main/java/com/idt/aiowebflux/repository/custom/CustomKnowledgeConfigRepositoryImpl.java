package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.response.*;
import com.idt.aiowebflux.response.KnowledgeConfigResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.idt.aiowebflux.entity.QAiModel.aiModel;
import static com.idt.aiowebflux.entity.QKnowledge.knowledge;
import static com.idt.aiowebflux.entity.QKnowledgeAiModel.knowledgeAiModel;
import static com.idt.aiowebflux.entity.QKnowledgeConfig.knowledgeConfig;

@Repository
@RequiredArgsConstructor
public class CustomKnowledgeConfigRepositoryImpl implements CustomKnowledgeConfigRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<KnowledgeModelNameConfigResponse> findModelCfgByKnowledgeId(final Long knowledgeId) {

        final KnowledgeConfigResponse knowledgeConfigResponse = queryFactory
                .select(
                        new QKnowledgeConfigResponse(
                                knowledgeConfig.knowledgeConfigId,
                                knowledgeConfig.knowledge.knowledgeId,
                                knowledgeConfig.overlapTokenR,
                                knowledgeConfig.resultCnt,
                                knowledgeConfig.scoreTh,
                                knowledgeConfig.retrievalCnt,
                                knowledgeConfig.retrievalWeightR
                        )
                ).from(knowledge)
                .join(knowledgeConfig).on(knowledgeConfig.knowledge.knowledgeId.eq(knowledge.knowledgeId))
                .where(knowledgeConfig.knowledge.knowledgeId.eq(knowledgeId))
                .fetchOne();

        final List<AiModelNameResponse> models = queryFactory.select(
                        new QAiModelNameResponse(
                                aiModel.name,
                                aiModel.feature
                        )
                )
                .from(aiModel)
                .join(knowledgeAiModel).on(aiModel.aiModelId.eq(knowledgeAiModel.aiModel.aiModelId))
                .where(knowledgeAiModel.knowledge.knowledgeId.eq(knowledgeId))
                .fetch();

        return Optional.of(new KnowledgeModelNameConfigResponse(
                models,
                knowledgeConfigResponse
        ));

    }

    @Override
    public Optional<KnowledgeModelConfigResponse> findKnowledgeModelConfigByKnowledgeId(final Long knowledgeId) {

       /* final KnowledgeConfigResponse knowledgeConfigResponse = queryFactory
                .select(
                        new QKnowledgeConfigResponse(
                                knowledgeConfig.knowledgeConfigId,
                                knowledgeConfig.knowledge.knowledgeId,
                                knowledgeConfig.overlapTokenR,
                                knowledgeConfig.resultCnt,
                                knowledgeConfig.scoreTh,
                                knowledgeConfig.retrievalCnt,
                                knowledgeConfig.retrievalWeightR
                        )
                ).from(knowledge)
                .join(knowledgeConfig).on(knowledgeConfig.knowledge.knowledgeId.eq(knowledge.knowledgeId))
                .where(knowledgeConfig.knowledge.knowledgeId.eq(knowledgeId))
                .fetchOne();

        final List<AiModelResponse> models = queryFactory.select(
                        new QAiModelResponse(
                                aiModel.aiModelId,
                                aiModel.modelCredential.modelCredentialId,
                                aiModel.name,
                                aiModel.provider,
                                aiModel.feature,
//                                aiModel.baseUrl,
                                aiModel.maxToken
                        )
                )
                .from(aiModel)
                .join(knowledgeAiModel).on(aiModel.aiModelId.eq(knowledgeAiModel.aiModel.aiModelId))
                .where(knowledgeAiModel.knowledge.knowledgeId.eq(knowledgeId).and(aiModel.feature.in(Feature.EMBEDDING, Feature.RERANKING))) // feature가 EMBEDDING, RERANKER인 것들만 조회
                .fetch();

        if (models.size() > 2 && validateModel(models)) {
            throw DomainExceptionCode.RERK_AND_EMB_MODEL_MORE_THAN_ONE.newInstance();
        }

        return Optional.of(new KnowledgeModelConfigResponse(
                models,
                knowledgeConfigResponse
        ));*/
        return null;

    }

/*    private boolean validateModel(final List<AiModelResponse> models) {
        return models.stream()
                .filter(model -> model.feature() == Feature.EMBEDDING)
                .count() == 1 && models.stream()
                .filter(model -> model.feature() == Feature.RERANKING)
                .count() == 1;
    }*/
}
