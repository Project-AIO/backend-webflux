package com.idt.aiowebflux.request;

public record KnowledgeConfigUpdateRequest(
        Float overlapTokenR,
        Integer resultCnt,
        Float scoreTh,
        Integer retrievalCnt,
        Float retrievalWeightR

) {

}
