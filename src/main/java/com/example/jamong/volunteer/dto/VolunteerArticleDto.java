package com.example.jamong.volunteer.dto;

import com.example.jamong.volunteer.domain.Picture;
import com.example.jamong.volunteer.domain.Volunteer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class VolunteerArticleDto {
    private final Long id;
    private final String title;
    private final String content;
    private final List<Picture> pictures;
    private final Long volunteerDate;
    private final Long applicationDate;
    private final int maximumPeople;
    private final int currentPeople;

    @Builder
    public VolunteerArticleDto(Volunteer entity) {
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

