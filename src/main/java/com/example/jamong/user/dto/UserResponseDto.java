package com.example.jamong.user.dto;

import com.example.jamong.user.domain.Role;
import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private String profileImage;
    private String email;
    private String name;
    private Role role;
    private String cardinalNumber;
    private List<Volunteer> volunteers;
    private List<Volunteer> favoriteVolunteers;

    @Builder
    public UserResponseDto(User entity, List<Volunteer> volunteers, List<Volunteer> favoriteVolunteers) {
        this.profileImage = entity.getProfileImage();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.role = entity.getRole();
        this.cardinalNumber = entity.getCardinalNumber();
        this.volunteers = volunteers;
        this.favoriteVolunteers = favoriteVolunteers;
    }

    public void addVolunteer(Volunteer volunteer) {
        this.volunteers.add(volunteer);
    }

    public void addFavoriteVolunteer(Volunteer volunteer) {
        this.favoriteVolunteers.add(volunteer);
    }

    public User toEntity() {
        return User.builder()
                .profileImage(profileImage)
                .email(email)
                .name(name)
                .role(role)
                .cardinalNumber(cardinalNumber)
                .build();
    }
}
