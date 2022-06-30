package com.example.jamong.volunteer.dto;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyListDto {
    private Long id;
    private User user;
    private Volunteer volunteer;
}
