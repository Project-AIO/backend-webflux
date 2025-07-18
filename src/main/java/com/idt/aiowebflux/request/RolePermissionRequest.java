package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RolePermissionRequest(
        @NotNull
        String roleName,
        @NotNull
        List<Long> permissionIds
) {
}
