package com.idt.aiowebflux.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.data.redis") // record 를 쓸 때 필수 (스프링 부트 3.x)
public record RedisProperties(
        String host,
        int port,
        String password,
        int database,
        Duration timeout,
        Pool pool,
        Map<String, Duration> ttl
) {
    public record Pool(int maxActive, int maxIdle, int minIdle) { }
}
