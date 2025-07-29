/*
package com.idt.aiowebflux.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@EnableRabbit
@RequiredArgsConstructor
@Configuration
public class RabbitMqConfig {
    // Spring -> Core
    //Spring -> Core 방향 통신에 쓰일 큐/교환기/라우팅 키
    public static final String FILE_CONTENT_QUEUE = "file-content-queue";
    public static final String FILE_CONTENT_EXCHANGE = "file-content-exchange";
    public static final String FILE_CONTENT_ROUTING_KEY = "file.content.key";
    // Core -> Spring (결과)
    public static final String FILE_CONTENT_RESULT_QUEUE = "file-content-result-queue";
    public static final String FILE_CONTENT_RESULT_EXCHANGE = "file-content-result-exchange";
    public static final String FILE_CONTENT_RESULT_ROUTING_KEY = "file.content.result.key";

    // 문서 대량 업로드 관련 RabbitMQ 설정
    public static final String FILE_BULK_UPLOAD_RESULT_QUEUE = "file-bulk-upload-result-queue";
    public static final String FILE_BULK_UPLOAD_RESULT_EXCHANGE = "file-bulk-upload-result-exchange";
    public static final String FILE_BULK_UPLOAD_RESULT_ROUTING_KEY = "file.bulk-upload.result.key";

    private final ObjectMapper objectMapper;

    // (1) Spring -> Core 큐 생성
    //durable = false이므로 운영환경에선 필요 시 true로 변경 필요 false는 메모리에 저장되기 때문에 서버 재시작 시 데이터 손실 가능
    @Primary
    @Bean("fileContentQueue")
    public Queue fileContentQueue() {
        return QueueBuilder.durable(FILE_CONTENT_QUEUE)
                .withArgument("x-message-ttl", 2592000000L) // 메시지 TTL 설정 (예: 1일 = 86400000밀리초)
                .withArgument("x-consumer-timeout", 86400000) // 소비자 타임아웃 설정 (예: 1일 = 86400000밀리초)
                .build();
    }

    //Direct Exchange(직접 교환기) 생성
    @Bean("fileContentExchange")
    public DirectExchange fileContentExchange() {
        return new DirectExchange(FILE_CONTENT_EXCHANGE);
    }

    //Queue와 Exchange 사이를 이어주는 Binding을 생성
    @Bean("fileContentBinding")
    public Binding fileContentBinding(@Qualifier("fileContentQueue") Queue fileContentQueue,
                                      @Qualifier("fileContentExchange") DirectExchange fileContentExchange) {
        return BindingBuilder.bind(fileContentQueue)
                .to(fileContentExchange)
                .with(FILE_CONTENT_ROUTING_KEY);
    }

    // (2) Core -> Spring
    @Bean("fileContentResultQueue")
    public Queue fileContentResultQueue() {
        return QueueBuilder.durable(FILE_CONTENT_RESULT_QUEUE)
                .withArgument("x-message-ttl", 2592000000L) // 메시지 TTL 설정 (예: 1일 = 86400000밀리초)
                .withArgument("x-consumer-timeout", 86400000) // 소비자 타임아웃 설정 (예: 1일 = 86400000밀리초)
                .build();
    }

    @Bean("fileContentResultExchange")
    public DirectExchange fileContentResultExchange() {
        return new DirectExchange(FILE_CONTENT_RESULT_EXCHANGE);
    }

    @Bean("fileContentResultBinding")
    public Binding fileContentResultBinding(@Qualifier("fileContentResultQueue") Queue fileContentResultQueue, @Qualifier("fileContentResultExchange") DirectExchange fileContentResultExchange) {
        return BindingBuilder.bind(fileContentResultQueue)
                .to(fileContentResultExchange)
                .with(FILE_CONTENT_RESULT_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        // Jackson2JsonMessageConverter: Java 객체 <-> JSON 자동 변환
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // 문서 대량 업로드 관련 RabbitMQ 설정

    @Bean("fileBulkUploadResultQueue")
    public Queue fileBulkUploadResultQueue() {
        return QueueBuilder.durable(FILE_BULK_UPLOAD_RESULT_QUEUE)
                .withArgument("x-message-ttl", 2592000000L) // 메시지 TTL 설정 (예: 1일 = 86400000밀리초)
                .withArgument("x-consumer-timeout", 86400000) // 소비자 타임아웃 설정 (예: 1일 = 86400000밀리초)
                .build();
    }

    @Bean("fileBulkUploadResultExchange")
    public DirectExchange fileBulkUploadResultExchange() {
        return new DirectExchange(FILE_BULK_UPLOAD_RESULT_EXCHANGE);
    }

    @Bean("fileBulkUploadResultBinding")
    public Binding fileBulkUploadResultBinding(@Qualifier("fileBulkUploadResultQueue") Queue fileBulkUploadResultQueue, @Qualifier("fileBulkUploadResultExchange") DirectExchange fileBulkUploadResultExchange) {
        return BindingBuilder.bind(fileBulkUploadResultQueue)
                .to(fileBulkUploadResultExchange)
                .with(FILE_BULK_UPLOAD_RESULT_ROUTING_KEY);
    }

}*/
