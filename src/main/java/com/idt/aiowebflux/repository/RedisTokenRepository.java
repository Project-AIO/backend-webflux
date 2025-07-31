package com.idt.aiowebflux.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository {
    private final RedisTemplate<String, Object> redis;
    private String accessKey(final String accountId) {
        return "ACCESS:" + accountId;
    }

    private String refreshKey(final String accountId) {
        return "REFRESH:" + accountId;
    }
    private String blacklistKey(final String jti) {
        return "BLACK:" + jti;
    }




    /* Refresh Token 보관 */
    public void saveRefresh(final String accountId, final String token, final Instant ttl) {
        final Duration duration = Duration.between(Instant.now(), ttl);
        redis.opsForValue().set(refreshKey(accountId), token, duration);
    }
    public void saveAccess(final String accountId, final String jti, final Instant ttl) {
        final Duration duration = Duration.between(Instant.now(), ttl);
        redis.opsForValue().set(accessKey(accountId), jti, duration);
    }

    public String getRefresh(final String accountId) {
        return (String) redis.opsForValue().get(refreshKey(accountId));
    }
    public String getAccessJti(final String accountId) {
        return (String) redis.opsForValue().get(accessKey(accountId));
    }
    public void deleteRefresh(final String accountId) {
        redis.delete(refreshKey(accountId));
    }
    public void deleteAccess(final String accountId) {
        redis.delete(accessKey(accountId));
    }

    public void syncAccountRoleChange(final String accountId){
        final String jti = (String)redis.opsForValue().get(accessKey(accountId));

        if (jti != null) {
            blacklist(jti, accountId);
            deleteAccess(accountId);
        }
    }

    /* 블랙리스트(로그아웃) */
    public void blacklist(final String jti, final Instant ttl) {
        final Duration duration = Duration.between(Instant.now(), ttl);
        redis.opsForValue().set(blacklistKey(jti), "1", duration);
    }

    public void blacklist(final String jti, final String accountId) {
        Long ttlSec = redis.getExpire(accessKey(accountId));   // 초 단위 TTL

        // ‑2: 키 없음, ‑1: 만료 없음
        if (ttlSec == null || ttlSec <= 0) {
            //즉시 만료시킨다
            redis.opsForValue().set(blacklistKey(jti), "1", Duration.ZERO);

        }

        redis.opsForValue().set(
                blacklistKey(jti),
                "1",
                Duration.ofSeconds(ttlSec)
        );
    }
    public boolean isBlacklisted(final String jti) {
        return redis.hasKey(blacklistKey(jti));
    }

    public boolean hasKey(final String accountId) {
        return redis.hasKey(accessKey(accountId)) || redis.hasKey(refreshKey(accountId));
    }

}
