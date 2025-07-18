package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.ModelDataDto;
import com.idt.aiowebflux.response.KnowledgeAiModelDataResponse;

import java.util.List;
import java.util.Optional;

public interface CustomKnowledgeAiModelRepository {
    Optional<ModelDataDto> findAiModelDataByKnowledgeId(final Long knowledgeId);

    void deleteByKnowledgeKnowledgeId(final Long knowledgeId);

    List<KnowledgeAiModelDataResponse> findModelDataByKnowledge_KnowledgeId(final Long knowledgeId);

}
