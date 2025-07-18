package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;

public record AccountPageRequest(
        @NotNull
        int page,
        @NotNull
        int size,
        @NotNull
        Sort.Direction direction,
        @NotNull
        String sortProperty
) {
}
