package com.example.jamong.volunteer.domain;

import com.example.jamong.config.BaseTimeEntity;
import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.dto.ApplyListResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ApplyList extends BaseTimeEntity {
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
    public ApplyList (User user, Volunteer volunteer) {
        this.user = user;
        this.volunteer = volunteer;
    }

    public ApplyListResponseDto toDto() {
        return ApplyListResponseDto.builder()
                .entity(this)
                .build();
    }
}
