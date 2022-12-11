package com.example.jamong.volunteer.repository;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Apply;
import com.example.jamong.volunteer.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyListRepository extends JpaRepository<Apply, Long> {

    List<Apply> findByVolunteer(Volunteer volunteer);

    List<Apply> findByUser(User user);

    void deleteByUserAndVolunteer(User user, Volunteer volunteer);

    List<Apply> findByUserAndVolunteer(User user, Volunteer volunteer);
}

