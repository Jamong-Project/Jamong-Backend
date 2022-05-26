package com.example.jamong.domain.volunteer.dto;

import lombok.Builder;

public class VolunteerUpdateRequestDto {
    private String title;
    private String content;
    private String picture;
    private String volunteerDate;
    private String applicationDate;
    private String maximumPerson;

    @Builder
    public VolunteerUpdateRequestDto(String title, String content, String picture, String volunteerDate, String applicationDate, String maximumPerson) {
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPerson = maximumPerson;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVolunteerDate() {
        return volunteerDate;
    }

    public void setVolunteerDate(String volunteerDate) {
        this.volunteerDate = volunteerDate;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getMaximumPerson() {
        return maximumPerson;
    }

    public void setMaximumPerson(String maximumPerson) {
        this.maximumPerson = maximumPerson;
    }

}
