package com.example.jamong.volunteer.dto;

import com.example.jamong.volunteer.domain.Picture;
import com.example.jamong.volunteer.domain.Volunteer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class VolunteerSaveRequestDto {
    private static final int INITIAL_CURRENT_PERSON_VALUE = 0;

    private final String title;
    private final String content;
    private List<Picture> pictures;
    private final Long volunteerDate;
    private final Long applicationDate;
    private final int maximumPeople;
    private int currentPeople;

    @Builder
    public VolunteerSaveRequestDto(String title, String content, List<Picture> pictures, Long volunteerDate, Long applicationDate, int maximumPeople, Integer currentPeople) {
        this.title = title;
        this.content = content;
        this.pictures = pictures;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPeople = maximumPeople;
        this. currentPeople = INITIAL_CURRENT_PERSON_VALUE;

        if (currentPeople != null) {
            this.currentPeople = currentPeople;
        }

    }

    public void setPictures(List<Picture> pictures) {
       this.pictures = pictures;
    }

    public Volunteer toEntity() {
        return Volunteer.builder()
                .title(title)
                .content(content)
                .pictures(pictures)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .currentPeople(currentPeople)
                .build();
    }

}
