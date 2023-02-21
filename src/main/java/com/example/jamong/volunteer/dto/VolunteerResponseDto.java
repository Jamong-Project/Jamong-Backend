package com.example.jamong.volunteer.dto;

import com.example.jamong.volunteer.domain.Picture;
import com.example.jamong.volunteer.domain.Volunteer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class VolunteerResponseDto {
    private Long id;
    private String title;
    private String content;
    private List<Picture> pictures;
    private Long volunteerDate;
    private Long applicationDate;
    private int maximumPeople;
    private int currentPeople;

    @Builder
    public VolunteerResponseDto(Volunteer entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.pictures = entity.getPictures();
        this.volunteerDate = entity.getVolunteerDate();
        this.applicationDate = entity.getApplicationDate();
        this.maximumPeople = entity.getMaximumPeople();
        this.currentPeople = entity.getCurrentPeople();
    }
}
