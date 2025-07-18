package com.idt.aiowebflux;

import com.idt.aiowebflux.config.UploadProps;
import com.idt.aiowebflux.registry.ProgressRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@ConfigurationPropertiesScan
@SpringBootApplication
public class AioWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(AioWebfluxApplication.class, args);
    }
    @Bean
    public ProgressRegistry progressRegistry() {
        return new ProgressRegistry(); // 싱글톤으로 컨테이너에 등록
    }

}
