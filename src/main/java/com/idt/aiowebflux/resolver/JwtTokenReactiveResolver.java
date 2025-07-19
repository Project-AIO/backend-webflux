package com.idt.aiowebflux.resolver;

import com.idt.aiowebflux.annotation.JwtContext;
import com.idt.aiowebflux.dto.JwtPrincipal;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerReactiveAuthenticationManagerResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtTokenReactiveResolver implements HandlerMethodArgumentResolver {

    private static final String PREFIX = "Bearer ";

    private final JwtIssuerReactiveAuthenticationManagerResolver amResolver;

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.hasParameterAnnotation(JwtContext.class)
                && JwtPrincipal.class.equals(param.getParameterType());
    }


    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext,
                                        ServerWebExchange exchange) {

        String auth = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (auth == null || !auth.startsWith(PREFIX)) {
            return Mono.error(DomainExceptionCode.JWT_NO_AUTHORIZATION_HEADER.newInstance());
        }

        String token = auth.substring(PREFIX.length());
        ReactiveAuthenticationManager mgr = amResolver.resolve(exchange)
                .blockOptional()
                .orElseThrow(() -> new ResponseStatusException(
                        org.springframework.http.HttpStatus.UNAUTHORIZED,
                        "Unknown JWT issuer"));

        return mgr.authenticate(new BearerTokenAuthenticationToken(token))
                .map(authResult -> {
                    Jwt jwt = (Jwt) authResult.getPrincipal();
                    return new JwtPrincipal(jwt.getSubject(),
                            jwt.getId(),
                            jwt.getExpiresAt());
                });
    }
}
