package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.response.ResourceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomKnowledgeRepository {
    Page<ResourceResponse> fetchAllKnowledgeByPage(final Pageable pageable);
}
