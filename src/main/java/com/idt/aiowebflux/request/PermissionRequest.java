package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PermissionRequest(
        @NotNull
        List<Long> permissionIds
) {
}
