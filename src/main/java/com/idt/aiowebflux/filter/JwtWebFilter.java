package com.idt.aiowebflux.filter;

import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.repository.ReactiveRedisTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtWebFilter implements WebFilter {

    private final ReactiveRedisTokenRepository tokenRepo;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String auth = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // 헤더가 없거나 "Bearer " 로 시작하지 않으면 그냥 다음 필터로
        if (!StringUtils.hasText(auth) || !auth.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = auth.substring(7);
        return tokenRepo.isBlackListed(token)
                .flatMap(blackListed ->
                        blackListed
                                ? Mono.error(DomainExceptionCode.JWT_BLACKLISTED_ERROR.newInstance())
                                : chain.filter(exchange));
    }
}
