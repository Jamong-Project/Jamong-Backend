package com.example.jamong.user.controller;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.TokenRequestDto;
import com.example.jamong.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
}
