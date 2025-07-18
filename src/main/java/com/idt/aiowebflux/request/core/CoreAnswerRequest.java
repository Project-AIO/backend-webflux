package com.idt.aiowebflux.request.core;

import com.idt.aiowebflux.request.DocumentFileDataRequest;
import com.idt.aiowebflux.request.KnowledgeAiModelDataRequest;

import java.util.List;

public record CoreAnswerRequest(
        Long conversationId,
        Long questionId,
        List<Message> messages,
        List<DocumentFileDataRequest> docs,
        List<KnowledgeAiModelDataRequest> aiModels,
        Integer retrievalCnt,
        Float scoreTh,
        Float retrievalWeightR,
        Integer resultCnt
) {
}
