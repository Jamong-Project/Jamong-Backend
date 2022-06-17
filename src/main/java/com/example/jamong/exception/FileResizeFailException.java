package com.example.jamong.exception;

import org.springframework.http.HttpStatus;

public class FileResizeFailException extends JamongException{
    private static final String MESSAGE = "파일 리사이징에 실패했습니다.";

    public FileResizeFailException() {
        super(MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
