package com.idt.aiowebflux.repository;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ReactiveRedisTokenRepository {

    private static final String KEY_PREFIX = "bltk:";   // black‑listed token
    private final RedisTemplate<String, Object> redis;
    private String blacklistKey(final String jti) {
        return "BLACK:" + jti;
    }
    /**
     * 블랙리스트에 등록
     */
    public boolean isBlacklisted(final String jti) {
        return redis.hasKey(blacklistKey(jti));
    }
}
