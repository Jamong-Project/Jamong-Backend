package com.example.jamong.user.dto;

import com.example.jamong.user.domain.Role;
import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserResponseDto {
    private String profileImage;
    private String gender;
    private String email;
    private String mobile;
    private String mobileE164;
    private String name;
    private Role role;
    private String cardinalNumber;
    private List<Volunteer> volunteers;
    private List<Volunteer> favoriteVolunteers;

    @Builder
    public UserResponseDto(User entity, List<Volunteer> volunteers, List<Volunteer> favoriteVolunteers) {
        this.profileImage = entity.getProfileImage();
        this.gender = entity.getGender();
        this.email = entity.getEmail();
        this.mobile = entity.getMobile();
        this.mobileE164 = entity.getMobileE164();
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
                .mobile(mobile)
                .mobileE164(mobileE164)
                .name(name)
                .role(role)
                .cardinalNumber(cardinalNumber)
                .build();
    }
}
