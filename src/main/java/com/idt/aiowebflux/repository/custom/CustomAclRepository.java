package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.AclDto;
import com.idt.aiowebflux.entity.constant.ResourceType;
import com.idt.aiowebflux.response.ResourceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAclRepository {
    Page<AclDto> findAllByPage(
            final Pageable pageable,
            final String searchField,
            final String searchInput);

    Page<ResourceResponse> findByResourceType(final Pageable pageable, final ResourceType resourceType);
}
