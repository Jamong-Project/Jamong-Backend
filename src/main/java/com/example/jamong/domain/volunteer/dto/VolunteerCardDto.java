package com.example.jamong.domain.volunteer.dto;

import com.example.jamong.domain.volunteer.Volunteer;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VolunteerCardDto {
    private final Long id;
    private final String title;
    private final String picture;
    private final Long volunteerDate;
    private final Long applicationDate;
    private final Integer maximumPeople;
    private final Integer currentPeople;

    @Builder
    public VolunteerCardDto(Volunteer entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.picture = entity.getPicture();
        this.volunteerDate = entity.getVolunteerDate();
        this.applicationDate = entity.getApplicationDate();
        this.maximumPeople = entity.getMaximumPeople();
        this.currentPeople = entity.getCurrentPeople();
    }
}
