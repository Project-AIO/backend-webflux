package com.idt.aiowebflux.repository;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ReactiveRedisTokenRepository {

    private static final String KEY_PREFIX = "bltk:";   // black‑listed token
    private final ReactiveStringRedisTemplate redis;

    /**
     * 블랙리스트에 등록
     */
    public Mono<Boolean> blacklist(String jwt, Duration ttl) {
        return redis.opsForValue()
                .set(KEY_PREFIX + jwt, "1", ttl)         // value 아무거나 OK
                .doOnSuccess(ok -> {
                    if (Boolean.TRUE.equals(ok)) {
                        // 필요하면 로그
                    }
                });
    }

    /**
     * 블랙리스트 여부 조회
     */
    public Mono<Boolean> isBlackListed(String jwt) {
        return redis.hasKey(KEY_PREFIX + jwt)
                .defaultIfEmpty(false);
    }
}
