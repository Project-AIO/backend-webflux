package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

//현재는 프로젝트당 embedding 모델과 reranker 모델이 1개만 존재한다고 가정
//gen 모델은 여러 개가 될 수 있음(프로젝트당)
public record QuestionRequest(
        Long conversationId,
        @NotNull
        String message,
        @NotNull
        Long knowledgeId,
        @NotNull
        List<String> docIds,
        String clientId,
        String genModelName
) {
}
