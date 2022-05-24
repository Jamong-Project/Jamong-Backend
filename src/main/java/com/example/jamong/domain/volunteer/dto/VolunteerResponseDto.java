package com.example.jamong.domain.volunteer.dto;

import com.example.jamong.domain.volunteer.Volunteer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VolunteerResponseDto {
    private final String title;
    private final String content;
    private final String picture;
    private final String dDay;
    private final String applyDay;
    private final String maximumPerson;

    @Builder
    public VolunteerResponseDto(Volunteer entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.picture = entity.getPicture();
        this.dDay = entity.getDDay();
        this.applyDay = entity.getApplyDay();
        this.maximumPerson = entity.getMaximumPerson();
    }
}
