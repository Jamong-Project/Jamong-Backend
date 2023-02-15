package com.example.jamong.volunteer.dto;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Application;
import com.example.jamong.volunteer.domain.Volunteer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplyResponseDto {
    private User user;
    private Volunteer volunteer;

    @Builder
    public ApplyResponseDto(Application entity) {
        this.user = entity.getUser();
        this.volunteer = entity.getVolunteer();
    }
}
