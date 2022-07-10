package com.example.jamong.volunteer.repository;

import com.example.jamong.volunteer.domain.Comment;
import com.example.jamong.volunteer.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByVolunteer(Volunteer entity);
}
