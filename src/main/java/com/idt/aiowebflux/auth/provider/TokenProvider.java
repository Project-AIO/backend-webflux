package com.idt.aiowebflux.auth.provider;

import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.repository.RedisTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String ACCOUNT_ID = "account_id";
    private static final String ISSUER = "iss";
    private final String secret;
    private final RedisTokenRepository tokenRepo; // 블랙리스트 관리용
    private static final int    CLOCK_SKEW_SEC = 60;    // 1분 허용
    private Key key;
    public TokenProvider(
            @Value("${jwt.secret}") final String secret,
            final RedisTokenRepository tokenRepo) {
        this.secret = secret;
        this.tokenRepo = tokenRepo; // 블랙리스트 관리용
    }
    @Override
    public void afterPropertiesSet() {
        final byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public Claims parseAndValidate(final String token) {
        try {
            return Jwts.parser()
                    .clockSkewSeconds(CLOCK_SKEW_SEC)
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("JWT expired at {}", e.getClaims().getExpiration());
            throw DomainExceptionCode.JWT_TOKEN_EXPIRED.newInstance(e.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.warn("JWT 형식 오류: {}", e.getMessage());
            throw DomainExceptionCode.JWT_MAL_FORMED.newInstance(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("빈 토큰 또는 null 전달");
            throw DomainExceptionCode.JWT_TOKEN_NOT_FOUND.newInstance(e.getMessage());
        }
    }
}
