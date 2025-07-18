package com.idt.aiowebflux.response.core;

import java.util.List;

public record CoreAnswerResponse(
        Long conversationId, //대화 ID
        //해당 질문에 대한 답변
        Long questionId,
        String state,
        String message,
        List<Ref> ref
) {
    public static CoreAnswerResponse empty() {
        return new CoreAnswerResponse(
                null,
                null,
                null,
                null,
                null
        );
    }
}
