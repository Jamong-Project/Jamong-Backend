package com.example.jamong.volunteer.repository;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Application;
import com.example.jamong.volunteer.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByVolunteer(Volunteer volunteer);

    List<Application> findByUser(User user);

    boolean existsByVolunteerIdAndUserEmail(Long volunteerId, String email);

    Application findByUserAndVolunteer(User user, Volunteer volunteer);
}

