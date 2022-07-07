package com.example.jamong.user.domain;

import com.example.jamong.config.BaseTimeEntity;
import com.example.jamong.user.dto.UserResponseDto;
import com.example.jamong.user.dto.UserUpdateRequestDto;
import com.example.jamong.volunteer.domain.ApplyList;
import com.example.jamong.volunteer.domain.Favorite;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naverId;

    private String profileImage;

    private String gender;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String mobileE164;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String cardinalNumber;

    @JsonBackReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<ApplyList> applyLists = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    @Builder
    public User(Long id, String naverId, String profileImage, String gender, String email, String mobile, String mobileE164, String name, Role role, String cardinalNumber) {
        this.id = id;
        this.naverId = naverId;
        this.profileImage = profileImage;
        this.gender = gender;
        this.email = email;
        this.mobile = mobile;
        this.mobileE164 = mobileE164;
        this.name = name;
        this.role = role;
        this.cardinalNumber = "New";

        if (cardinalNumber != null) {
            this.cardinalNumber = cardinalNumber;
        }
    }

    public void update(UserUpdateRequestDto requestDto) {
        if (requestDto.getProfileImage() != null) {
            this.profileImage = requestDto.getProfileImage();
        }

        if (requestDto.getEmail() != null) {
            this.email = requestDto.getEmail();
        }

        if (requestDto.getMobile() != null) {
            String mobileNumber = requestDto.getMobile();
            this.mobile = mobileNumber;
            this.mobileE164 = "+82" + mobileNumber.replace("-", "").substring(1, 10);
        }

        if (requestDto.getRole() != null) {
            this.role = requestDto.getRole();
        }

        if (requestDto.getCardinalNumber() != null) {
            this.cardinalNumber = requestDto.getCardinalNumber();
        }
    }

    public UserResponseDto toDto() {
        return UserResponseDto.builder()
                .entity(this)
                .build();
    }
}