package com.example.jamong.domain.volunteer.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VolunteerUpdateRequestDto {
    private final String title;
    private final String content;
    private final String picture;
    private final String volunteerDate;
    private final String applicationDate;
    private final String maximumPerson;

    @Builder
    public VolunteerUpdateRequestDto(String title, String content, String picture, String volunteerDate, String applicationDate, String maximumPerson) {
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPerson = maximumPerson;
    }
}