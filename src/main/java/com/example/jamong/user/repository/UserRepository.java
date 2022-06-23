package com.example.jamong.user.repository;

import com.example.jamong.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);

    List<User> findByName(String name);
}
