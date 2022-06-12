package com.example.jamong.domain.volunteer.dto;

import com.example.jamong.domain.picture.Picture;
import com.example.jamong.domain.volunteer.Volunteer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class VolunteerCardDto {
    private final Long id;
    private final String title;
    private final List<Picture> picture;
    private final Long volunteerDate;
    private final Long applicationDate;
    private final Integer maximumPeople;
    private final Integer currentPeople;

    @Builder
    public VolunteerCardDto(Volunteer entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.picture = entity.getPictures();
        this.volunteerDate = entity.getVolunteerDate();
        this.applicationDate = entity.getApplicationDate();
        this.maximumPeople = entity.getMaximumPeople();
        this.currentPeople = entity.getCurrentPeople();
    }
}
