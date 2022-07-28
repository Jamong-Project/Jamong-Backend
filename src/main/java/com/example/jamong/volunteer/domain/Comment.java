package com.example.jamong.volunteer.domain;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.dto.CommentResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Volunteer volunteer;

    @Column(length = 500)
    private String content;

    @Builder
    public Comment(User user, Volunteer volunteer, String content) {
        this.user = user;
        this.volunteer = volunteer;
        this.content = content;
    }

    public CommentResponseDto toDto() {
        return CommentResponseDto.builder()
                .id(this.id)
                .email(user.getEmail())
                .name(user.getName())
                .content(content)
                .build();
    }
}
