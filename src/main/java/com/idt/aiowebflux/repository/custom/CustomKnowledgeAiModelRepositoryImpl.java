package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.ModelDataDto;
import com.idt.aiowebflux.dto.QModelDataDto;
import com.idt.aiowebflux.entity.constant.Feature;
import com.idt.aiowebflux.response.KnowledgeAiModelDataResponse;
import com.idt.aiowebflux.response.QAiModelNameResponse;
import com.idt.aiowebflux.response.QKnowledgeAiModelDataResponse;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.idt.aiowebflux.entity.QAiModel.aiModel;
import static com.idt.aiowebflux.entity.QKnowledgeAiModel.knowledgeAiModel;

@RequiredArgsConstructor
@Repository
public class CustomKnowledgeAiModelRepositoryImpl implements CustomKnowledgeAiModelRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ModelDataDto> findAiModelDataByKnowledgeId(final Long knowledgeId) {


        final ModelDataDto modelDataDto = queryFactory
                .select(
                        new QModelDataDto(
//                                knowledgeAiModel.apiKey,
                                Expressions.constant("apiKey"), // modelCredential.apiKey,
                                aiModel.name,
//                                aiModel.baseUrl,
                                Expressions.constant("baseUrl"), // modelCredential.baseUrl,
                                aiModel.feature
                        )
                )
                .from(knowledgeAiModel)
                .join(aiModel).on(knowledgeAiModel.aiModel.aiModelId.eq(aiModel.aiModelId))
                .where(knowledgeAiModel.knowledge.knowledgeId.eq(knowledgeId),
                        aiModel.feature.eq(Feature.EMBEDDING)
                )
                .fetchOne();
        return Optional.ofNullable(modelDataDto);
    }

    @Override
    public void deleteByKnowledgeKnowledgeId(Long knowledgeId) {
        queryFactory
                .delete(knowledgeAiModel)
                .where(knowledgeAiModel.knowledge.knowledgeId.eq(knowledgeId))
                .execute();
    }

    @Override
    public List<KnowledgeAiModelDataResponse> findModelDataByKnowledge_KnowledgeId(final Long knowledgeId) {


        return queryFactory.select(
                        new QKnowledgeAiModelDataResponse(
                                knowledgeAiModel.knowledge.knowledgeId,
                                aiModel.aiModelId,
//                                knowledgeAiModel.apiKey,
//                                knowledgeAiModel.temperature,
//                                knowledgeAiModel.topP,
                                knowledgeAiModel.useMaxToken,
//                                knowledgeAiModel.topK,
                                new QAiModelNameResponse(
                                        aiModel.name,
                                        aiModel.feature
                                )
                        )
                )
                .from(aiModel)
                .join(knowledgeAiModel).on(aiModel.aiModelId.eq(knowledgeAiModel.aiModel.aiModelId))
                .where(knowledgeAiModel.knowledge.knowledgeId.eq(knowledgeId))
                .fetch();
    }
}
