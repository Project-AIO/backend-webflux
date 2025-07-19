package com.idt.aiowebflux.auth.decoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import reactor.core.publisher.Mono;

public class ReactiveJjwtDecoder implements ReactiveJwtDecoder {

    private final SecretKey key;     // ✅ 구체 타입

    public ReactiveJjwtDecoder(@Nonnull SecretKey key) {
        this.key = key;
    }

    @Override
    public Mono<Jwt> decode(String token) {
        return Mono.fromCallable(() -> doDecode(token))
                // CPU‑bound라 논블로킹 문제는 없지만, 필요하면 boundedElastic 스케줄러 사용 가능
                .onErrorMap(JwtException.class, ex -> ex);   // JwtException 그대로 전달
    }

    /* 실제 파싱 로직 (기존 MVC 버전과 동일) */
    private Jwt doDecode(String token) {

        Jws<Claims> jws = Jwts.parser()          // ✔ parser()가 Builder를 반환
                .verifyWith(key)   // ✔ setSigningKey → verifyWith
                .build()           //   Builder → Parser
                .parseSignedClaims(token);   // 0.12.x 방식

        Claims claims = jws.getPayload();        // 0.12.x는 getPayload()
        Map<String, Object> headers = new HashMap<>(jws.getHeader());
        Map<String, Object> claimsMap = new HashMap<>(claims);

        Instant issuedAt = extractInstant(claims.get("iat"));
        Instant expiresAt = extractInstant(claims.get("exp"));

        return Jwt.withTokenValue(token)
                .headers(h -> h.putAll(headers))
                .claims(c -> c.putAll(claimsMap))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .subject(claims.getSubject())
                .build();
    }

    private Instant extractInstant(Object value) {
        if (value instanceof Date d) {
            return d.toInstant();
        }
        if (value instanceof Long l) {
            return Instant.ofEpochSecond(l);
        }
        if (value instanceof Integer i) {
            return Instant.ofEpochSecond(i.longValue());
        }
        return null;
    }
}
