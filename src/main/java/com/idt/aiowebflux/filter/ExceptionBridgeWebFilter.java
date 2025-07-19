package com.idt.aiowebflux.filter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class ExceptionBridgeWebFilter implements WebFilter {

    private final WebExceptionHandler exHandler;

    public ExceptionBridgeWebFilter(
            @Qualifier("errorWebExceptionHandler")  // ★ 어떤 빈을 쓸지 지정
            WebExceptionHandler exHandler) {
        this.exHandler = exHandler;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(e -> exHandler.handle(exchange, e));
    }
}