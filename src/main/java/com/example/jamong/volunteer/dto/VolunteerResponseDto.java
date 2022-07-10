package com.example.jamong.volunteer.dto;

import com.example.jamong.volunteer.domain.ApplyList;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Picture;
import com.example.jamong.volunteer.domain.Volunteer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
public class VolunteerResponseDto {
    private Long id;
    private String title;
    private String content;
    private List<Picture> pictures;
    private Long volunteerDate;
    private Long applicationDate;
    private int maximumPeople;
    private int currentPeople;
    private List<ApplyList> applyLists;
    private List<Favorite> favorites;

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
        this.applyLists = entity.getApplyLists();
        this.favorites = entity.getFavorites();
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
