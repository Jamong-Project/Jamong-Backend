package com.example.jamong.volunteer.dto;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FavoriteResponseDto {
    private User user;
    private Volunteer volunteer;

    @Builder
    public FavoriteResponseDto(Favorite entity) {
        this.user = entity.getUser();
        this.volunteer = entity.getVolunteer();
    }
}
