package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.Extension;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.servlet.multipart")
public class FileSpec {
    private String maxFileSize;
    private String maxRequestSize;
    private final Set<String> allowedFileExtensions= Extension.getExtensions();
    private final Set<String> allowedMimeTypes= Extension.getTypes();

}
