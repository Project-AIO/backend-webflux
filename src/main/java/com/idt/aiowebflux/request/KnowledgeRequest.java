package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record KnowledgeRequest(
        @NotNull
        @Size(min = 1, max = 100)
        String name,

        @NotNull
        String accountId,

        String description
) {
}
