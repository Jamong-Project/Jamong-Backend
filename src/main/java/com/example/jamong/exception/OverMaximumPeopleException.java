package com.example.jamong.exception;

import org.springframework.http.HttpStatus;

public class OverMaximumPeopleException extends JamongException{
    private static final String MESSAGE = "정원을 초과하여 신청할 수 없는 봉사입니다.";

    public OverMaximumPeopleException() {
        super(MESSAGE, HttpStatus.NOT_ACCEPTABLE);
    }
}
