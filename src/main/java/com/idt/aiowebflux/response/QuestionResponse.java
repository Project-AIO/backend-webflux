package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.Question;

import java.util.List;

public record QuestionResponse(
        Long questionId,
        Long conversationId,
        String message
) {
    public static QuestionResponse from(final Question question) {
        return new QuestionResponse(
                question.getQuestionId(),
                question.getConversation().getConversationId(),
                question.getMessage()
        );
    }

    public static List<QuestionResponse> from(final List<Question> questions) {
        return questions.stream()
                .map(QuestionResponse::from)
                .toList();
    }
}
