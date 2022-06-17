package com.example.jamong.exception;

import org.springframework.http.HttpStatus;

public class FromBiggerThanToException extends JamongException{
    private static final String MESSAGE = "from은 to보다 클 수 없습니다.";

    public FromBiggerThanToException() {
        super(MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
