package com.example.jamong.volunteer.dto;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Comment;
import com.example.jamong.volunteer.domain.Picture;
import com.example.jamong.volunteer.domain.Volunteer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VolunteerArticleResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final List<Picture> pictures;
    private final Long volunteerDate;
    private final Long applicationDate;
    private final int maximumPeople;
    private final int currentPeople;
    private final List<User> applicants;
    private final List<User> favoriteUsers;
    private final List<Comment> comments;

    @Builder
    public VolunteerArticleResponseDto(Volunteer entity, List<Picture> pictures, List<User> applicants, List<User> favoriteUsers, List<Comment> comments) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.pictures = pictures;
        this.volunteerDate = entity.getVolunteerDate();
        this.applicationDate = entity.getApplicationDate();
        this.maximumPeople = entity.getMaximumPeople();
        this.currentPeople = entity.getCurrentPeople();
        this.applicants = applicants;
        this.favoriteUsers = favoriteUsers;
        this.comments = comments;
    }
}

