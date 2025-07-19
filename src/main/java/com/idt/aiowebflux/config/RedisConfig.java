package com.idt.aiowebflux.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.idt.aiowebflux.config.redis.RedisProperties;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
@RequiredArgsConstructor
public class RedisConfig {
    private final RedisProperties props;

    @Bean(destroyMethod = "shutdown")              // 안전한 종료
    ClientResources lettuceClientResources() {
        return DefaultClientResources.builder().build();
    }
    @Bean
    LettuceConnectionFactory redisConnectionFactory(final ClientResources clientResources) {
        // Stand-alone. Sentinel/Cluster 는 별도 builder 사용
        final RedisStandaloneConfiguration standalone = new RedisStandaloneConfiguration(
                props.host(), props.port());
        standalone.setDatabase(props.database());
        if (props.password() != null && !props.password().isBlank()) {
            standalone.setPassword(RedisPassword.of(props.password()));
        }

        // Pool 설정
        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(props.pool().maxActive());
        poolConfig.setMaxIdle(props.pool().maxIdle());
        poolConfig.setMinIdle(props.pool().minIdle());

        final LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientResources(clientResources)
                .commandTimeout(props.timeout())
                .shutdownTimeout(Duration.ofSeconds(2))
                .build();

        return new LettuceConnectionFactory(standalone, clientConfig);
    }

    // 공통 Serializer – Jackson(PolymorphicTypeValidator 필수) */
    @Bean
    GenericJackson2JsonRedisSerializer jsonSerializer(final ObjectMapper om) {
        final ObjectMapper copy = om.copy() //글로벌 ObjectMapper 오염 방지용 복사본
                .activateDefaultTyping(
                        BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build(), //역직렬화 타입 검증기 (모든 타입 허용 – 내부 사용이므로 OK)
                        ObjectMapper.DefaultTyping.NON_FINAL) //"final이 아닌" 클래스에만 @class 메타데이터 부여
                .setSerializationInclusion(JsonInclude.Include.NON_NULL); // null 필드는 직렬화에서 제외하여 저장 공간 최소화
        return new GenericJackson2JsonRedisSerializer(copy);
    }

    // RedisTemplate<String, Object> – Key:String, Value:JSON
    @Bean
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(
            ReactiveRedisConnectionFactory factory) {

        StringRedisSerializer keySer = new StringRedisSerializer();
        StringRedisSerializer valSer = new StringRedisSerializer();

        RedisSerializationContext<String, String> ctx = RedisSerializationContext
                .<String, String>newSerializationContext(keySer)
                .value(valSer)
                .build();

        return new ReactiveStringRedisTemplate(factory, ctx);
    }

    // CacheManager – Cache별 TTL · null 값 캐싱 OFF
    @Bean
    CacheManager cacheManager(final GenericJackson2JsonRedisSerializer json) {
        // 기본 설정: Value 직렬화 + null 캐싱 off
        final RedisCacheConfiguration baseConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(json))
                .disableCachingNullValues();

        //캐시별 개별 TTL 적용
        //@Cacheable("user-cache") 같이 쓰는 순간 해당 캐시 항목은 1시간이 지나면 알아서 사라짐
        /**
         * application.yml 참고
         *       ttl:
         *         short: 10s
         *         default: 1h
         *         user-cache: 3h
         */
        final Map<String, RedisCacheConfiguration> perCache = props.ttl().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> baseConfig.entryTtl(e.getValue())));

        //connectionFactory는 직접 주입하지 못하므로 메서드 호출
        return RedisCacheManager.builder(redisConnectionFactory(lettuceClientResources()))
                .cacheDefaults(baseConfig.entryTtl(props.ttl().getOrDefault("default", Duration.ofHours(1))))
                .withInitialCacheConfigurations(perCache)
                .transactionAware()
                .build();
    }


}
