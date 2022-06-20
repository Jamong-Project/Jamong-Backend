package com.example.jamong.user.domain;

import com.example.jamong.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
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
}
