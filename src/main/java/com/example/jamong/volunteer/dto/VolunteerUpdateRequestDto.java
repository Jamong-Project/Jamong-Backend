package com.example.jamong.volunteer.dto;

import com.example.jamong.volunteer.domain.Picture;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class VolunteerUpdateRequestDto {
    private final String title;
    private final String content;
    private List<Picture> pictures;
    private final Long volunteerDate;
    private final Long applicationDate;
    private final Integer maximumPeople;
    private final Integer currentPeople;

    @Builder
    public VolunteerUpdateRequestDto(String title, String content, List<Picture> pictures,
                                     Long volunteerDate,
                                     Long applicationDate,
                                     Integer maximumPeople, Integer currentPeople) {

        this.title = title;
        this.content = content;
        this.pictures = pictures;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPeople = maximumPeople;
        this.currentPeople = currentPeople;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}