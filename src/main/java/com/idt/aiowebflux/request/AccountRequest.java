package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

public record AccountRequest(
        @NotNull
        String accountId,

        @NotNull
        String name) {
}
