package com.example.jamong.volunteer.repository;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByVolunteer(Volunteer volunteer);

    List<Favorite> findByUser(User user);

    Favorite findByUserAndVolunteer(User user, Volunteer volunteer);

    boolean existsByVolunteerIdAndUserEmail(Long volunteerId, String email);

    void deleteByUserAndVolunteer(User user, Volunteer volunteer);
}
