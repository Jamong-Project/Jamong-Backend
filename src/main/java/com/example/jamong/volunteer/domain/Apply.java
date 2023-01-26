package com.example.jamong.volunteer.domain;

import com.example.jamong.config.BaseTimeEntity;
import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.dto.ApplyResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Apply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Volunteer volunteer;

    @Builder
    public Apply(User user, Volunteer volunteer) {
        this.user = user;
        this.volunteer = volunteer;
    }

    public ApplyResponseDto toDto() {
        return ApplyResponseDto.builder()
                .entity(this)
                .build();
    }
}
