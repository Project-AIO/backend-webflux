package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.entity.AiModel;
import com.idt.aiowebflux.response.ResourceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomAiModelRepository {
    List<AiModel> findByKnowledgeId(final Long knowledgeId);

    Page<ResourceResponse> fetchAllAiModelsByPage(final Pageable pageable);
}
