package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.CoreDataDto;

public interface CustomCoreRepository {
    CoreDataDto findKnowledgeAiModelDataByKnowledgeId(final Long knowledgeId, final String genModelName);
}
