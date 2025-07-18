package com.idt.aiowebflux.dto;

import com.querydsl.core.annotations.QueryProjection;

public record ConversationData(
        Long questionId,
        String questionMessage,
        Long answerId,
        String answerMessage
) {
    @QueryProjection
    public ConversationData {
    }
}
