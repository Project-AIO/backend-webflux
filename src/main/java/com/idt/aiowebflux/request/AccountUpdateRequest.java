package com.idt.aiowebflux.request;

public record AccountUpdateRequest(
        String name,
        RoleRequest roleRequest
) {
}
