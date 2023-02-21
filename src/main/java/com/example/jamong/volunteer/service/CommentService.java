package com.example.jamong.volunteer.service;

import com.example.jamong.volunteer.domain.Comment;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getCommentsByVolunteer(Volunteer entity) {
        List<Comment> comments = commentRepository.findByVolunteer(entity);
        return comments;
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
