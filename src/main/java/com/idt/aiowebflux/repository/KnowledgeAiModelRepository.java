package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.KnowledgeAiModel;
import com.idt.aiowebflux.entity.composite.KnowledgeAiModelId;
import com.idt.aiowebflux.entity.constant.Feature;
import com.idt.aiowebflux.repository.custom.CustomKnowledgeAiModelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowledgeAiModelRepository extends JpaRepository<KnowledgeAiModel, KnowledgeAiModelId>, CustomKnowledgeAiModelRepository {
//    @Query("SELECT kam.apiKey FROM KnowledgeAiModel pam WHERE kam.knowledge.knowledgeId = :knowledgeId AND kam.aiModel.aiModelId = :aiModelId")
//    Optional<String> findApiKeyByKnowledgeIdAndAiModel_AiModelId(@Param("knowledgeId") final Integer knowledgeId, @Param("aiModelId") final Integer aiModelId);

    @Query("SELECT kam FROM KnowledgeAiModel kam WHERE kam.knowledge.knowledgeId = :knowledgeId AND kam.aiModel.aiModelId IN :aiModelIds")
    List<KnowledgeAiModel> findByKnowledge_KnowledgeIdAndAiModel_AiModelIdsInIn(@Param("knowledgeId") final Long knowledgeId,
                                                                            @Param("aiModelIds") final List<Long> aiModelIds);

    @Query("SELECT kam FROM KnowledgeAiModel kam JOIN kam.aiModel am WHERE kam.knowledge.knowledgeId = :knowledgeId AND am.feature = :feature")
    List<KnowledgeAiModel> findByKnowledge_KnowledgeIdAndAiModel_Feature(@Param("knowledgeId") final Long knowledgeId,
                                                                     @Param("feature") final Feature feature);
}
