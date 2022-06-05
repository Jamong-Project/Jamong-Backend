package com.example.jamong.domain.volunteer.dto;

import com.example.jamong.domain.volunteer.Volunteer;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class VolunteerSaveRequestDto {
    private final String title;
    private final String content;
    private final String picture;


    @DateTimeFormat
    private final LocalDateTime volunteerDate;

    @DateTimeFormat
    private final LocalDateTime applicationDate;

    private final Integer maximumPeople;
    private final Integer currentPeople;

    @Builder
    public VolunteerSaveRequestDto(String title, String content, String picture, LocalDateTime volunteerDate, LocalDateTime applicationDate, Integer maximumPeople, Integer currentPeople) {
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPeople = maximumPeople;
        this.currentPeople = currentPeople;
    }

    public Volunteer toEntity() {
        return Volunteer.builder()
                .title(title)
                .content(content)
                .picture(picture)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .build();
    }
}
