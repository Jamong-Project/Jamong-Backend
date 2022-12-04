package com.example.jamong.volunteer.dto;

import com.example.jamong.volunteer.domain.Picture;
import com.example.jamong.volunteer.domain.Volunteer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VolunteerCardDto {

    private final Long id;
    private final String title;
    private final Picture picture;
    private final Long volunteerDate;
    private final Long applicationDate;
    private final int maximumPeople;
    private final int currentPeople;

    @Builder
    public VolunteerCardDto(Volunteer entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.picture = entity.representPicture();
        this.volunteerDate = entity.getVolunteerDate();
        this.applicationDate = entity.getApplicationDate();
        this.maximumPeople = entity.getMaximumPeople();
        this.currentPeople = entity.getCurrentPeople();
    }
}
