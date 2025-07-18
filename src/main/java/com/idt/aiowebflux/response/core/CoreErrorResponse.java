package com.idt.aiowebflux.response.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoreErrorResponse {

    private Integer code;
    private String message;
    private String traceback;

    public CoreErrorResponse() {
        this.code = null;
        this.message = null;
        this.traceback = null;
    }

    public static CoreErrorResponse of(Integer code, String message, String traceback) {
        return new CoreErrorResponse(code, message, traceback);
    }
}
