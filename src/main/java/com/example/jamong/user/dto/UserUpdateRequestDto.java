package com.example.jamong.user.dto;

import com.example.jamong.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserUpdateRequestDto {
    private String profileImage;
    private String email;
    private Role role;
    private String cardinalNumber;
}
