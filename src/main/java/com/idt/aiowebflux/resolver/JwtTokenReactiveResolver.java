package com.idt.aiowebflux.resolver;

import com.idt.aiowebflux.annotation.JwtContext;
import com.idt.aiowebflux.auth.provider.TokenProvider;
import com.idt.aiowebflux.dto.JwtPrincipal;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
public class JwtTokenReactiveResolver implements HandlerMethodArgumentResolver {

    private static final String PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.hasParameterAnnotation(JwtContext.class)
                && JwtPrincipal.class.equals(param.getParameterType());
    }


    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext,
                                        ServerWebExchange exchange) {
        Mono<JwtPrincipal> fromCtx = ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth.getPrincipal() instanceof JwtPrincipal)
                .map(auth -> (JwtPrincipal) auth.getPrincipal());

        Mono<JwtPrincipal> fromHeader = extractToken(exchange)
                .flatMap(token ->
                        Mono.fromCallable(() -> tokenProvider.parseAndValidate(token))
                                .subscribeOn(Schedulers.boundedElastic())   // 서명검증이 무거울 경우 대비
                                .map(this::toPrincipal)
                );

        return fromCtx
                .switchIfEmpty(fromHeader)
                .cast(Object.class);   // Mono<JwtPrincipal> → Mono<Object>
    }

    private Mono<String> extractToken(ServerWebExchange exchange) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.startsWith(PREFIX)) {
            return Mono.error(DomainExceptionCode.JWT_NO_AUTHORIZATION_HEADER.newInstance());
        }
        return Mono.just(header.substring(PREFIX.length()));
    }

    private JwtPrincipal toPrincipal(Claims claims) {
        String accountId = claims.get("account_id", String.class);
        if (accountId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "missing account_id claim");
        }
        return new JwtPrincipal(
                accountId,
                claims.getId(),                       // jti
                claims.getExpiration().toInstant()    // 만료 시간
        );
    }
}
