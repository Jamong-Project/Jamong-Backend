package com.example.jamong.domain.volunteer.dto;

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

    private final Integer maximumPeople;
    private final Integer currentPeople;

    @Builder
    public VolunteerUpdateRequestDto(String title, String content, String picture,
                                     LocalDateTime volunteerDate,
                                     LocalDateTime applicationDate,
                                     Integer maximumPeople, Integer currentPeople) {

        this.title = title;
        this.content = content;
        this.picture = picture;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPeople = maximumPeople;
        this.currentPeople = currentPeople;
    }
}