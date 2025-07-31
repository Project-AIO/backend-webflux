package com.idt.aiowebflux.config;

import com.idt.aiowebflux.filter.ExceptionBridgeWebFilter;
import com.idt.aiowebflux.filter.JwtWebFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

@RequiredArgsConstructor
@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class SecurityConfig {
    private final ServerAuthenticationEntryPoint entryPoint;         // 401
    private final ServerAccessDeniedHandler accessDeniedHandler;     // 403
    private final ExceptionBridgeWebFilter exceptionBridgeWebFilter; // ControllerAdvice 위임


    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http, JwtWebFilter jwtWebFilter,
                                                 CorsWebFilter corsWebFilter) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .addFilterBefore(corsWebFilter, SecurityWebFiltersOrder.CORS)
                .addFilterBefore(jwtWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAfter(exceptionBridgeWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeExchange(ex -> ex
                        .pathMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/signup", "/api/v1/signin").permitAll()
                        .pathMatchers("/api/v1/login/sso", "/error").permitAll()
                        .anyExchange().authenticated())
                .build();
    }


    /**
     * CorsWebFilter Bean (필요 시)
     */
    @Bean
    CorsWebFilter corsWebFilter(CorsConfigurationSource cs) {
        return new CorsWebFilter(cs);
    }

}