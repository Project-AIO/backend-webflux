package com.idt.aiowebflux.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAuthenticationEntryPointReactive implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        log.warn("Unauthorized request to {} : {}", exchange.getRequest().getURI(), ex.getMessage());
        return Mono.fromRunnable(() -> exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED));
    }
}
