package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.PrincipalType;
import com.idt.aiowebflux.entity.constant.ResourceType;
import com.querydsl.core.annotations.QueryProjection;

import java.util.List;
import java.util.Map;

public record AclDto(
        PrincipalType principalType,
        Long principalId,
        String principalName,
        Map<ResourceType, List<ResourceAccessPermissionDto>> resourceIdToNameMap
) {
    @QueryProjection
    public AclDto {
    }
}
