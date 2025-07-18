package com.idt.aiowebflux.exception;

import java.io.Serial;

public class JwtTokenExpired extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1222176482713735628L;

    public JwtTokenExpired(final String message) {
        super(message);
    }

}
