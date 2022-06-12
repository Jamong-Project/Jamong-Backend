package com.example.jamong.domain.volunteer.dto;

import com.example.jamong.domain.picture.Picture;
import com.example.jamong.domain.volunteer.Volunteer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class VolunteerSaveRequestDto {
    private final String title;
    private final String content;
    private List<Picture> pictures;
    private final Long volunteerDate;
    private final Long applicationDate;
    private final Integer maximumPeople;
    private final Integer currentPeople;

    @Builder
    public VolunteerSaveRequestDto(String title, String content, List<Picture> pictures, Long volunteerDate, Long applicationDate, Integer maximumPeople, Integer currentPeople) {
        this.title = title;
        this.content = content;
        this.pictures = pictures;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPeople = maximumPeople;
        this.currentPeople = currentPeople;
    }

    public List<Picture> setPictures(List<Picture> picture) {
        this.pictures = picture;
        return picture;
    }

    public Volunteer toEntity() {
        return Volunteer.builder()
                .title(title)
                .content(content)
                .pictures(pictures)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .build();
    }
}
