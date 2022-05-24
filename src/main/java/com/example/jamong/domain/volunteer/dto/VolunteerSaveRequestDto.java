package com.example.jamong.domain.volunteer.dto;

import com.example.jamong.domain.volunteer.Volunteer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VolunteerSaveRequestDto {
    private final String title;
    private final String content;
    private final String picture;
    private final String volunteerDate;
    private final String applicationDate;
    private final String maximumPerson;

    @Builder
    public VolunteerSaveRequestDto(String title, String content, String picture, String volunteerDate, String applicationDate, String maximumPerson) {
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPerson = maximumPerson;
    }

    public Volunteer toEntity() {
        return Volunteer.builder()
                .title(title)
                .content(content)
                .picture(picture)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPerson(maximumPerson)
                .build();
    }
}
