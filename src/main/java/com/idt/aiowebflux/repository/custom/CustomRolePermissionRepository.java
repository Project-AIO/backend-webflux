package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.RolePermissionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomRolePermissionRepository {
    Page<RolePermissionDto> findAllByPage(final Pageable pageable,
                                          final String searchField,
                                          final String searchInput);
}
