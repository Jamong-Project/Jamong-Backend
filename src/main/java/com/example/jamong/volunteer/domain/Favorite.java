package com.example.jamong.volunteer.domain;

import com.example.jamong.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Favorite {
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
    public Favorite(User user, Volunteer volunteer) {
        this.user = user;
        this.volunteer = volunteer;
    }
}
