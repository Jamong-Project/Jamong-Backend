package com.example.jamong.user.dto;

import com.example.jamong.user.domain.Role;
import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Volunteer;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Getter
public class UserResponseDto {
    private String profileImage;
    private String gender;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String mobile;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String cardinalNumber;

    private List<Volunteer> volunteers;

    @Builder
    public UserResponseDto(User entity, List<Volunteer> volunteers) {
        this.profileImage = entity.getProfileImage();
        this.gender = entity.getGender();
        this.email = entity.getEmail();
        this.mobile = entity.getMobile();
        this.name = entity.getName();
        this.role = entity.getRole();
        this.cardinalNumber = entity.getCardinalNumber();
        this.volunteers = volunteers;
    }
}
