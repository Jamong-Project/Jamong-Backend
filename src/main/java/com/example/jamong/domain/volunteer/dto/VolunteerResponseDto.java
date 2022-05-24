package com.example.jamong.domain.volunteer.dto;

import com.example.jamong.domain.volunteer.Volunteer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VolunteerResponseDto {
    private final String title;
    private final String picture;
    private final String volunteerDate;
    private final String applicationDate;
    private final String maximumPerson;

    @Builder
    public VolunteerResponseDto(Volunteer entity) {
        this.title = entity.getTitle();
        this.picture = entity.getPicture();
        this.volunteerDate = entity.getVolunteerDate();
        this.applicationDate = entity.getApplicationDate();
        this.maximumPerson = entity.getMaximumPerson();
    }
}
