package com.example.jamong.user.dto;

import com.example.jamong.user.domain.Role;
import com.example.jamong.user.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserSaveRequestDto {
    @JsonProperty("id")
    private String naverId;

    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    public User toEntity() {
        return User.builder()
                .naverId(naverId)
                .profileImage(profileImage)
                .email(email)
                .name(name)
                .role(Role.GUEST)
                .build();
    }
}
