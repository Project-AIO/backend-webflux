package com.idt.aiowebflux.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtAccessDeniedHandlerReactive implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        log.warn("Access denied to {} : {}", exchange.getRequest().getURI(), ex.getMessage());
        return Mono.fromRunnable(() -> exchange.getResponse()
                .setStatusCode(HttpStatus.FORBIDDEN));
    }
}