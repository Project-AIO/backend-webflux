package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.Conversation;
import com.idt.aiowebflux.entity.Question;


public record ConversationQuestion(
        Conversation conversation,
        Question question
) {
}
