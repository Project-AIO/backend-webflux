package com.idt.aiowebflux.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class ExternalServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1222342482738735628L;
    private final String errorMsg;
    private HttpStatus httpStatus;

    public ExternalServiceException(final String errorMsg, final HttpStatus httpStatus) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.httpStatus = httpStatus;
    }

    public ExternalServiceException(final String errorMsg, final Throwable cause) {
        super(errorMsg, cause);
        this.errorMsg = errorMsg;
    }

}
