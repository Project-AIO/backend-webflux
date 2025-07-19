package com.idt.aiowebflux.entity.constant;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mime {
    PDF("application/pdf");

    private final String value;

    //set에다가 다 담아서 set반환
    public static Set<String> getValues() {
        return Arrays.stream(Mime.values())
                .map(Mime::getValue)
                .collect(Collectors.toSet());
    }

    public static boolean isValidMimeType(String mimeType) {
        return getValues().stream().anyMatch(mimeType::startsWith);
    }
}
