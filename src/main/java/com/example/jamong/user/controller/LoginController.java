package com.example.jamong.user.controller;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.TokenRequestDto;
import com.example.jamong.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final UserService userService;

    @PostMapping("/v1/login")
    public ResponseEntity<User> login(@RequestBody TokenRequestDto tokenRequestDto) {
        return userService.getProfile(tokenRequestDto);
    }
}
