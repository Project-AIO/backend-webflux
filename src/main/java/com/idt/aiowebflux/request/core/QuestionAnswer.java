package com.idt.aiowebflux.request.core;

import com.querydsl.core.annotations.QueryProjection;

public record QuestionAnswer(
        String question,
        String answer
) {
    @QueryProjection
    public QuestionAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
