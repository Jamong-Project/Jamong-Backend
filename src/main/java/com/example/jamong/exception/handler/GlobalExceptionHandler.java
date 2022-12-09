package com.example.jamong.exception.handler;

import com.example.jamong.exception.*;
import com.example.jamong.exception.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoExistVolunteerException.class)
    public ResponseEntity<ErrorResponseDto> NoExistVolunteerException(NoExistVolunteerException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(NoExistUserException.class)
    public ResponseEntity<ErrorResponseDto> NoExistUserException(NoExistUserException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(FileResizeFailException.class)
    public ResponseEntity<ErrorResponseDto> FileResizeFailException(FileResizeFailException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(OverMaximumPeopleException.class)
    public ResponseEntity<ErrorResponseDto> OverMaximumPeopleException(OverMaximumPeopleException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(NaverLoginFailException.class)
    public ResponseEntity<ErrorResponseDto> NaverLoginFailException(NaverLoginFailException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ErrorResponseDto(exception.getMessage()));
    }
}