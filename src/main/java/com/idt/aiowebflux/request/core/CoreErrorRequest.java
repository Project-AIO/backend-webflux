package com.idt.aiowebflux.request.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoreErrorRequest {

    private Integer code;
    private String message;
    private String traceback;

    public static CoreErrorRequest of(Integer code, String message, String traceback) {
        return new CoreErrorRequest(code, message, traceback);
    }

    public static CoreErrorRequest empty() {
        return new CoreErrorRequest(
                null,
                null,
                null
        );
    }
}
