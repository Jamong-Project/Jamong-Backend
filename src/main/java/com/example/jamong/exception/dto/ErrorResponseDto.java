package com.example.jamong.exception.dto;

public class ErrorResponseDto {

    private String message;

    public ErrorResponseDto(String message) {
        this.message = message;
    }

    public static ErrorResponseDto of(String message){
        return new ErrorResponseDto(message);
    }

    public String getMessage() {
        return message;
    }
}
