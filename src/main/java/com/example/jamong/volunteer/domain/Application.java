package com.example.jamong.volunteer.domain;

import com.example.jamong.config.BaseTimeEntity;
import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.dto.ApplyResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Application extends BaseTimeEntity {
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
    public Application(User user, Volunteer volunteer) {
        this.user = user;
        this.volunteer = volunteer;
    }

    public ApplyResponseDto toDto() {
        return ApplyResponseDto.builder()
                .entity(this)
                .build();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Application that = (Application) obj;

        if(!user.equals(that.user)) return false;
        return Objects.equals(volunteer, that.volunteer);
    }
}
