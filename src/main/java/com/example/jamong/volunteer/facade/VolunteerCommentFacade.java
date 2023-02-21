package com.example.jamong.volunteer.facade;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.service.UserService;
import com.example.jamong.volunteer.domain.Comment;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.CommentRequestDto;
import com.example.jamong.volunteer.service.CommentService;
import com.example.jamong.volunteer.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Component
public class VolunteerCommentFacade {
    private final UserService userService;
    private final VolunteerService volunteerService;
    private final CommentService commentService;
    @Transactional
    public Comment addComment(Long volunteerId, CommentRequestDto requestDto) {
        User user = userService.getUserByEmail(requestDto.getEmail());
        Volunteer volunteer = volunteerService.getVolunteerById(volunteerId);

        Comment comment = Comment.builder()
                .user(user)
                .volunteer(volunteer)
                .content(requestDto.getContent())
                .build();

        volunteer.addComment(comment);
        return commentService.save(comment);
    }
}
