package com.example.jamong.volunteer.domain;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.dto.FavoriteResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Volunteer volunteer;

    @Builder
    public Favorite(User user, Volunteer volunteer) {
        this.user = user;
        this.volunteer = volunteer;
    }

    public FavoriteResponseDto toDto() {
        return FavoriteResponseDto.builder()
                .entity(this)
                .build();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Favorite favorite = (Favorite) o;

        if (!user.equals(favorite.user)) return false;
        return Objects.equals(favorite.volunteer, volunteer);
    }
}
