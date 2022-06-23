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
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody TokenRequestDto tokenRequestDto) {
        return userService.getProfile(tokenRequestDto);
    }

    @GetMapping
    public List<User> findAll(@RequestParam(required = false) String email, @RequestParam(required = false) String name) {
        return userService.findAll(email, name);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.update(id, userUpdateRequestDto);
    }
}
