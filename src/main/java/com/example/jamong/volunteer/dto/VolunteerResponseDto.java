package com.example.jamong.volunteer.dto;

import com.example.jamong.volunteer.domain.Apply;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Picture;
import com.example.jamong.volunteer.domain.Volunteer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VolunteerResponseDto {
    private Long id;
    private String title;
    private String content;
    private List<Picture> pictures;
    private Long volunteerDate;
    private Long applicationDate;
    private int maximumPeople;
    private int currentPeople;
    private List<Apply> applies;
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
        this.applies = entity.getApplies();
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
