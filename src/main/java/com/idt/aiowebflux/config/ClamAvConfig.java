package com.idt.aiowebflux.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.capybara.clamav.ClamavClient;

@Configuration
public class ClamAvConfig {
    @Value("${clamav.host}")
    private String host;

    @Value("${clamav.port}")
    private int port;

    @Bean
    public ClamavClient clamavClient() {
        // 기본 포트(3310)가 아닌 커스텀 포트를 사용하려면 두 번째 인자로 넘기면 됩니다.
        return new ClamavClient(host, port);
    }
}
