package com.example.jamong.exception;

import org.springframework.http.HttpStatus;

public class NoExistUserException extends JamongException {
    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public NoExistUserException() {
        super(MESSAGE, HttpStatus.NO_CONTENT);
    }
}
