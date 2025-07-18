package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.Permission;
import com.querydsl.core.annotations.QueryProjection;

import java.util.List;

public record PermissionDto(
        Long permissionId,
        String displayName,
        String description
) {
    @QueryProjection
    public PermissionDto{

    }

    public static List<PermissionDto> from(final List<Permission> permissions) {
        return permissions.stream()
                .map(permission -> new PermissionDto(
                        permission.getPermissionId(),
                        permission.getDisplayName(),
                        permission.getDescription()))
                .toList();
    }
}
