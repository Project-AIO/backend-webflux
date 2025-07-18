package com.idt.aiowebflux.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.util.Set;

public record RolePermissionDto(
        Long roleId,
        String roleName,
        Set<PermissionDto> permissions
) {
    @QueryProjection
    public RolePermissionDto {

    }
}
