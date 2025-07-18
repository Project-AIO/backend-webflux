package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.*;
import com.idt.aiowebflux.dto.KnowledgeAiModelDataDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.idt.aiowebflux.entity.QAiModel.aiModel;
import static com.idt.aiowebflux.entity.QKnowledge.knowledge;
import static com.idt.aiowebflux.entity.QKnowledgeAiModel.knowledgeAiModel;

@RequiredArgsConstructor
@Repository
public class CustomCoreRepositoryImpl implements CustomCoreRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 현재는 프로젝트당 embedding 모델과 reranker 모델이 1개만 존재한다고 가정
     * gen 모델은 여러 개가 될 수 있음(프로젝트당)
     */
    @Override
    public CoreDataDto findKnowledgeAiModelDataByKnowledgeId(final Long knowledgeId, final String genModelName) {
        final List<KnowledgeAiModelDataDto> knowledgeAiModelResults = queryFactory
                .select(
                        new QKnowledgeAiModelDataDto(
                                knowledgeAiModel.knowledge.knowledgeId,
                                knowledgeAiModel.aiModel.aiModelId,
//                                knowledgeAiModel.aiModel.feature,
//                                knowledgeAiModel.aiModel.roleName,
//                                knowledgeAiModel.apiKey,
                                knowledgeAiModel.useMaxToken
//                                knowledgeAiModel.aiModel.baseUrl,
//                                knowledgeAiModel.topP,
//                                knowledgeAiModel.topK,
//                                knowledgeAiModel.temperature
                        )
                )
                .from(knowledgeAiModel)
                .join(knowledgeAiModel.knowledge, knowledge)
                .join(knowledgeAiModel.aiModel, aiModel)
                .where(knowledgeAiModel.knowledge.knowledgeId.eq(knowledgeId))
                .fetch();

        final Map<Long, KnowledgeAiModelDataDto> knowledgeaiModelDataDtoMap = knowledgeAiModelResults
                .stream()
                .collect(Collectors.toMap(
                        KnowledgeAiModelDataDto::aiModelId,
                        Function.identity()
                ));

        //현재 Emb, Rerk 모델은 하나씩 있고 gen은 여러개가 한 프로젝트 ID에 대해 tb_knowledge_ai_model에 저장되어 있음
        //이때 genModelName은 unique함으로 이를 통해 하나를 가져옴으로 각 feature마다 하나씩 가져오게 됨
//        final List<Long> aiModelIds = knowledgeAiModelResults.stream()
//                .filter((res) ->
//                        (res.feature().equals(Feature.EMBEDDING) || res.feature().equals(Feature.RERANKER))
//                                || res.roleName().equals(genModelName))
//                .map(KnowledgeAiModelDataDto::aiModelId)
//                .toList();
//
//        final Map<Feature, AiModelDataDto> aiModelDataDtoMap = queryFactory
//                .select(
//                        new QAiModelDataDto(
//                                aiModel.aiModelId,
//                                aiModel.roleName,
//                                aiModel.feature
//                        )
//                )
//                .from(aiModel)
//                .where(aiModel.aiModelId.in(aiModelIds))
//                .fetch()
//                .stream()
//                .collect(Collectors.toMap(
//                        AiModelDataDto::feature,
//                        Function.identity()
//                ));
//
//        final List<KnowledgeAiModelDataRequest> modelRequest = aiModelDataDtoMap.values()
//                .stream()
//                .map(aiModelDataDto -> new KnowledgeAiModelDataRequest(
//                        aiModelDataDto.feature(),
//                        aiModelDataDto.roleName(),
//                        knowledgeaiModelDataDtoMap.get(aiModelDataDto.aiModelId()).apiKey(),
//                        knowledgeaiModelDataDtoMap.get(aiModelDataDto.aiModelId()).useMaxToken(),
//                        knowledgeaiModelDataDtoMap.get(aiModelDataDto.aiModelId()).baseUrl(),
//                        knowledgeaiModelDataDtoMap.get(aiModelDataDto.aiModelId()).topP(),
//                        knowledgeaiModelDataDtoMap.get(aiModelDataDto.aiModelId()).topK(),
//                        knowledgeaiModelDataDtoMap.get(aiModelDataDto.aiModelId()).temperature()
//                ))
//                .toList();
//
//        if (!aiModelDataDtoMap.containsKey(Feature.GENERATION)) {
//            throw DomainExceptionCode.GEN_AI_MODEL_NOT_FOUND.newInstance();
//        }
//
//
//        final KnowledgeConfigDataDto knowledgeConfigDataDto = Optional.ofNullable(queryFactory
//                        .select(
//                                new QKnowledgeConfigDataDto(
//                                        knowledgeConfig.resultCnt,
//                                        knowledgeConfig.scoreTh,
//                                        knowledgeConfig.retrievalCnt,
//                                        knowledgeConfig.retrievalWeightR
//                                )
//                        )
//                        .from(knowledgeConfig)
//                        .where(knowledgeConfig.knowledge.knowledgeId.eq(knowledgeId))
//                        .fetchOne())
//                .orElseThrow(DomainExceptionCode.KNOWLEDGE_CFG_NOT_FOUND::newInstance);
//
//        return new CoreDataDto(
//                modelRequest,
//                knowledgeConfigDataDto.resultCnt(),
//                knowledgeConfigDataDto.scoreTh(),
//                knowledgeConfigDataDto.retrievalCnt(),
//                knowledgeConfigDataDto.retrievalWeightR()
//        );
        return null;
    }
}
