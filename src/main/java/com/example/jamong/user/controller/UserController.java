package com.example.jamong.user.controller;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.TokenRequestDto;
import com.example.jamong.user.dto.UserUpdateRequestDto;
import com.example.jamong.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Getter
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody TokenRequestDto tokenRequestDto) {
        return userService.getProfile(tokenRequestDto);
    }

    @GetMapping("/v1/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/v1/users/id/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/v1/users/email")
    public User findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/v1/users/name")
    public List<User> findByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    @PatchMapping("/v1/users/{id}")
    public User update(@PathVariable Long id, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.update(id, userUpdateRequestDto);
    }
}
