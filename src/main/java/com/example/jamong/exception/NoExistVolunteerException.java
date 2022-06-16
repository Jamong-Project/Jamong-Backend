package com.example.jamong.exception;

import org.springframework.http.HttpStatus;

public class NoExistVolunteerException extends JamongException {
    private static final String MESSAGE = "존재하지 않는 게시물 id 입니다.";

    public NoExistVolunteerException() {
        super(MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
