package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.response.ResourceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAgentRepository {
    Page<ResourceResponse> fetchAllAgentsByPage(final Pageable pageable);
}
