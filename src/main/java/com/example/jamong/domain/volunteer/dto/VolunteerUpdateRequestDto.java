package com.example.jamong.domain.volunteer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class VolunteerUpdateRequestDto {
    private final String title;
    private final String content;
    private final String picture;

    @DateTimeFormat
    private final LocalDateTime volunteerDate;

    @DateTimeFormat
    private final LocalDateTime applicationDate;

    private final Integer maximumPerson;

    @Builder
    public VolunteerUpdateRequestDto(
            String title, String content, String picture,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime volunteerDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm") LocalDateTime applicationDate,
            Integer maximumPerson) {

        this.title = title;
        this.content = content;
        this.picture = picture;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPerson = maximumPerson;
    }
}