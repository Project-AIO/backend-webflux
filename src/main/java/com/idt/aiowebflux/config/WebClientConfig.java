package com.idt.aiowebflux.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Configuration
public class WebClientConfig {
    private final ObjectMapper objectMapper;          // Spring Boot 기본( camel )
    private final ObjectMapper snakeCaseObjectMapper; // 우리가 만든 ( snake )

    @Bean
    public WebClient webClient(ObjectMapper objectMapper,
                               ObjectMapper snakeCaseObjectMapper) {

        return WebClient.builder()
                .codecs(cfg -> {
                    cfg.defaultCodecs().jackson2JsonDecoder(
                            new Jackson2JsonDecoder(snakeCaseObjectMapper));
                    cfg.defaultCodecs().jackson2JsonEncoder(
                            new Jackson2JsonEncoder(objectMapper));
                })
                .build();
    }
    @Bean
    public CodecCustomizer snakeCaseRequestCodecCustomizer() {
        return configurer -> {
            // 디코딩만 snake
            configurer.defaultCodecs()
                    .jackson2JsonDecoder(new Jackson2JsonDecoder(snakeCaseObjectMapper));

            // 인코딩은 기존 camel
            configurer.defaultCodecs()
                    .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));

            // 필요하면 maxInMemorySize 설정
            // configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
        };
    }

    @Bean
    public WebClient snakeCaseWebClient(ObjectMapper snakeCaseObjectMapper) {
        return WebClient.builder()
                .codecs(cfg -> {
                    cfg.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(snakeCaseObjectMapper));
                    cfg.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(snakeCaseObjectMapper));
                })
                .build();
    }
}
