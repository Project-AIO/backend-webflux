package com.idt.aiowebflux.exception;

import java.io.Serial;

public class InvalidFileNameException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1222179582713735628L;

    public InvalidFileNameException(final String message) {
        super(message);
    }

}
