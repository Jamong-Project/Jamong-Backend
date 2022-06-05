package com.example.jamong.domain.volunteer.dto;

import com.example.jamong.domain.volunteer.Volunteer;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class VolunteerResponseDto {
    private final Long id;
    private final String title;
    private final String picture;

    @DateTimeFormat
    private final LocalDateTime volunteerDate;

    @DateTimeFormat
    private final LocalDateTime applicationDate;

    private final Integer maximumPerson;
    private final Integer currentPerson;

    @Builder
    public VolunteerResponseDto(Volunteer entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.picture = entity.getPicture();
        this.volunteerDate = entity.getVolunteerDate();
        this.applicationDate = entity.getApplicationDate();
        this.maximumPerson = entity.getMaximumPerson();
        this.currentPerson = entity.getCurrentPerson();
    }
}
