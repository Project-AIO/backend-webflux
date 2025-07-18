package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.request.KnowledgeAiModelDataRequest;

import java.util.List;

public record CoreDataDto(
        List<KnowledgeAiModelDataRequest> aiModels,
        Integer resultCnt,
        Float scoreTh,
        Integer retrievalCnt,
        Float retrievalWeightR
) {
}
