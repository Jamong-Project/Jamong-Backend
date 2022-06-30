package com.example.jamong.volunteer.repository;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.ApplyList;
import com.example.jamong.volunteer.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyListRepository extends JpaRepository<ApplyList, Long> {

    List<ApplyList> findByVolunteer(Volunteer volunteer);

    List<ApplyList> findByUser(User user);
}

