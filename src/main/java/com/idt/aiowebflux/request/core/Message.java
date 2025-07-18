package com.idt.aiowebflux.request.core;

import com.querydsl.core.annotations.QueryProjection;

public record Message(
        String question,
        String answer
) {

    @QueryProjection
    public Message {

    }
}
