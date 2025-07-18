package com.idt.aiowebflux.request;

import com.idt.aiowebflux.entity.constant.PrincipalType;
import com.idt.aiowebflux.entity.constant.ResourceType;

public record AclRequest(
        PrincipalType principalType,
        String principalId,
        ResourceType resourceType
) {
}
