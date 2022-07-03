package com.example.jamong.exception;

import org.springframework.http.HttpStatus;

public class NaverLoginFailException extends JamongException {
    private static final String MESSAGE = "네이버 로그인에 실패했습니다.";

    public NaverLoginFailException() {
        super(MESSAGE, HttpStatus.BAD_REQUEST);
    }
}
