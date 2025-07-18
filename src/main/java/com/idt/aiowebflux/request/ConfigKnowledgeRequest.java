package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

public record ConfigKnowledgeRequest(
        @NotNull
        Float overlapTokenR,

        @NotNull
        Integer resultCnt,
        @NotNull
        Float scoreTh,
        @NotNull
        Integer retrievalCnt,
        @NotNull
        Float retrievalWeightR

) {

}
