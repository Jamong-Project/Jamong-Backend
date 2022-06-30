package com.example.jamong.volunteer.domain;

import com.example.jamong.config.BaseTimeEntity;
import com.example.jamong.user.domain.User;
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
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "VOLUNTEER_ID")
    private Volunteer volunteer;

    @Builder
    public ApplyList(User user, Volunteer volunteer) {
        this.id = id;
        this.user = user;
        this.volunteer = volunteer;
    }
}
