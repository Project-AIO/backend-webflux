package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AccountRoleRequest(
        @NotNull
        String accountId,
        @NotNull
        String name,
        List<Long> addRoleIds
) {
}
