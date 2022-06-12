package com.example.jamong.domain.volunteer.dto;

import com.example.jamong.domain.picture.Picture;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class VolunteerUpdateRequestDto {
    private final String title;
    private final String content;
    private final List<Picture> picture;
    private final Long volunteerDate;
    private final Long applicationDate;
    private final Integer maximumPeople;
    private final Integer currentPeople;

    @Builder
    public VolunteerUpdateRequestDto(String title, String content, List<Picture> picture,
                                     Long volunteerDate,
                                     Long applicationDate,
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