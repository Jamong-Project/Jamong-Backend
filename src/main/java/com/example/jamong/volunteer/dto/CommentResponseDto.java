package com.example.jamong.volunteer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
    private String email;
    private String name;
    private String content;
}
