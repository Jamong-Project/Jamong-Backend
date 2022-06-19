package com.example.jamong.exception;

import org.springframework.http.HttpStatus;

public class JamongException extends RuntimeException {
    private HttpStatus status;

    public JamongException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public JamongException(final String message, final Throwable cause, final HttpStatus status) {
        super(message, cause);
        this.status = status;
    }
}
