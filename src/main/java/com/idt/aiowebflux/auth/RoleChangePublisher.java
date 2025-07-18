package com.idt.aiowebflux.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleChangePublisher {
    private final RedisTemplate<String, Object> redis;
    private static final String CHANNEL = "acl.role.channel";

    public void publish(String roleCode) {
        redis.convertAndSend(CHANNEL, roleCode);
    }
}
