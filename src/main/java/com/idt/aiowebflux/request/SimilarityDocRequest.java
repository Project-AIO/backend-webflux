package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

public record SimilarityDocRequest(
        @NotNull
        String docId,
        @NotNull
        Long answerId,
        @NotNull
        int page,
        Float score
) {
}
