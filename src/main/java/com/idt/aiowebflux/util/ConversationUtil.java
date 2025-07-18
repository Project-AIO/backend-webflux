package com.idt.aiowebflux.util;

import com.idt.aiowebflux.entity.Conversation;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ConversationUtil {
    public boolean isExpired(final Conversation conversation) {
        final LocalDateTime retrievedTime = conversation.getCreateDt();
        LocalDateTime currentTime = LocalDateTime.now();

        final Duration duration = Duration.between(retrievedTime, currentTime);
        //24시간(1440분) 초과 여부 반환
        return duration.toHours() >= 24;
    }
}
