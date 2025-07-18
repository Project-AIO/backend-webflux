package com.idt.aiowebflux.response.core.test;

import com.idt.aiowebflux.response.core.Ref;

import java.util.List;

public record TestCoreAnswerResponse(
        Long conversation_id,
        Long question_id,
        String state,
        String message,
        List<Ref> ref
) {
}
