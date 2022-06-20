package com.example.jamong.user.domain;

import com.example.jamong.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Builder
    public User(Long id, String naverId, String profileImage, String gender, String email, String mobile, String mobileE164, String name, Role role) {
        this.id = id;
        this.naverId = naverId;
        this.profileImage = profileImage;
        this.gender = gender;
        this.email = email;
        this.mobile = mobile;
        this.mobileE164 = mobileE164;
        this.name = name;
        this.role = role;
    }
}
