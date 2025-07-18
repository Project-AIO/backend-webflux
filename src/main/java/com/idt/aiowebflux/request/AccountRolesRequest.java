package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AccountRolesRequest(
        @NotNull
        List<String> accountId,
        @NotNull
        List<Long> addRoleIds
) {
}
