package com.idt.aiowebflux.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleChangeSubscriber implements MessageListener {

    private final RolePermCache cache;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String role = new String(message.getBody());
        cache.evict(role);
    }
}
