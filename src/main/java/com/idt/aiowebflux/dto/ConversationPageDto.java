package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.response.core.Ref;
import com.querydsl.core.annotations.QueryProjection;

import java.util.List;


public record ConversationPageDto(
        Long questionId,
        String questionMessage,
        Long answerId,
        String answerMessage,
        List<Ref> refer
) {
    @QueryProjection
    public ConversationPageDto {
    }

}