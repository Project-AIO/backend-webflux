package com.idt.aiowebflux.config;
import org.springdoc.core.customizers.OpenApiCustomizer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.common.base.CaseFormat;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenApiCustomizer snakeCasePropertiesCustomiser() {
        return openApi -> {
            if (openApi.getComponents() == null) return;
            Map<String, Schema> schemas = openApi.getComponents().getSchemas();
            if (schemas == null) return;

            schemas.values().forEach(this::convertSchemaPropertiesToSnakeCase);
        };
    }

    private void convertSchemaPropertiesToSnakeCase(Schema<?> schema) {
        Map<String, Schema> props = schema.getProperties();
        if (props == null || props.isEmpty()) {
            convertExampleToSnakeCase(schema); // fallback: example만 바꾸기
            return;
        }

        Map<String, Schema> newProps = new LinkedHashMap<>();
        props.forEach((k, v) -> {
            String snake = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, k);
            newProps.put(snake, v);
        });
        schema.setProperties(newProps);

        // example도 있다면 키 변환
        convertExampleToSnakeCase(schema);
    }

    private void convertExampleToSnakeCase(Schema<?> schema) {
        Object ex = schema.getExample();
        if (!(ex instanceof Map<?,?> exampleMap)) return;

        Map<String,Object> snake = new LinkedHashMap<>();
        exampleMap.forEach((k,v) -> {
            String snakeKey = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, k.toString());
            snake.put(snakeKey, v);
        });
        schema.setExample(snake);
    }
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer snakeCase() {
        return builder -> builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
}
