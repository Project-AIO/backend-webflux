package com.idt.aiowebflux.filter;

import com.idt.aiowebflux.auth.provider.TokenProvider;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.repository.ReactiveRedisTokenRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtWebFilter implements WebFilter {

    private final ReactiveRedisTokenRepository tokenRepo;
    private final TokenProvider tokenProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);                  // 인증 헤더 없으면 그대로 진행
        }

        final String token = authHeader.substring(7);
        final Claims claims = tokenProvider.parseAndValidate(token);
        final String jti = claims.getId();

        if (tokenRepo.isBlacklisted(jti)){
            return Mono.error(DomainExceptionCode.JWT_BLACKLISTED.newInstance("Token is blacklisted"));
        }

        final Authentication auth = this.toAuthentication(claims);

        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }
    private Authentication toAuthentication(Claims claims) {

        // 예시: account_id, roles 두 클레임이 존재한다고 가정
        String accountId = claims.get("account_id", String.class);

        @SuppressWarnings("unchecked")
        List<String> roleCodes = claims.get("roles", List.class);

        Collection<SimpleGrantedAuthority> authorities =
                roleCodes == null
                        ? List.of()
                        : roleCodes.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        return new UsernamePasswordAuthenticationToken(
                accountId,
                claims.getId(),
                authorities
        );
    }
}
