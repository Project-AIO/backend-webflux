package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.response.KnowledgeModelConfigResponse;
import com.idt.aiowebflux.response.KnowledgeModelNameConfigResponse;

import java.util.Optional;

public interface CustomKnowledgeConfigRepository {
    Optional<KnowledgeModelNameConfigResponse> findModelCfgByKnowledgeId(final Long knowledgeId);

    Optional<KnowledgeModelConfigResponse> findKnowledgeModelConfigByKnowledgeId(final Long knowledgeId);
}
