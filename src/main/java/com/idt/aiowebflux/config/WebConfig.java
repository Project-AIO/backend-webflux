package com.idt.aiowebflux.config;

import com.idt.aiowebflux.resolver.JwtTokenReactiveResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebFluxConfigurer {
    private final JwtTokenReactiveResolver resolver;

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer cfg) {
        cfg.addCustomResolver(resolver);
    }
}
