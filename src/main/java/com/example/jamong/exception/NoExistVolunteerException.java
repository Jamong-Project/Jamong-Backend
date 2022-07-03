package com.example.jamong.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class NoExistVolunteerException extends JamongException {
    private static final String MESSAGE = "존재하지 않는 게시물 id 입니다.";

    public NoExistVolunteerException() {
        super(MESSAGE, HttpStatus.NO_CONTENT);
    }
}
