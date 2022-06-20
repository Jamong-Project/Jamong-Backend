package com.example.jamong.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NaverResponseDto {
    @JsonProperty("resultcode")
    private String resultCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("response")
    private UserSaveRequestDto userSaveRequestDto;
}
