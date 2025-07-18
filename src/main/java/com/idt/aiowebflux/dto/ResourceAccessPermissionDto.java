package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.PermissionMask;
import com.idt.aiowebflux.entity.constant.ResourceType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class ResourceAccessPermissionDto{
    private final Long resourceId;
    private final ResourceType resourceType;
    private final String resourceName;
    private final Integer permMask;
    private final List<PermissionMask> permissionMasks;

    @QueryProjection
    public ResourceAccessPermissionDto(final Long resourceId, final ResourceType resourceType, final String resourceName, final Integer permMask) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.resourceName = resourceName;
        this.permMask = permMask;
        this.permissionMasks = PermissionMask.fromMask(permMask);
    }

}

