package com.example.jamong.volunteer.dto;

import com.example.jamong.volunteer.domain.Picture;
import com.example.jamong.volunteer.domain.Volunteer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class VolunteerSaveRequestDto {
    private static final int INITIAL_CURRENT_PERSON_VALUE = 0;

    private String title;
    private String content;
    private List<Picture> pictures;
    private Long volunteerDate;
    private Long applicationDate;
    private int maximumPeople;
    private int currentPeople;

    @Builder
    public VolunteerSaveRequestDto(Volunteer entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.pictures = entity.getPictures();
        this.volunteerDate = entity.getVolunteerDate();
        this.applicationDate = entity.getApplicationDate();
        this.maximumPeople = entity.getMaximumPeople();
        this.currentPeople = entity.getCurrentPeople();
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
