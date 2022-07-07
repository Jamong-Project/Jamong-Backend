package com.example.jamong.user.controller;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserResponseDto;
import com.example.jamong.user.dto.UserUpdateRequestDto;
import com.example.jamong.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Getter
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(@RequestParam(required = false) String email, @RequestParam(required = false) String name) {
        List<User> users = userService.findAll(email, name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        UserResponseDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        User updated = userService.update(id, userUpdateRequestDto);
        return ResponseEntity.created(URI.create("/v1/users/" + updated.getId())).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
