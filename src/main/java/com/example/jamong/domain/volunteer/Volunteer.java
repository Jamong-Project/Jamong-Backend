package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.*;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Entity
public class Volunteer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String picture;
    private String volunteerDate;
    private String applicationDate;
    private String maximumPerson;

    @Builder
    public Volunteer(Long id, String title, String content, String picture, String volunteerDate, String applicationDate, String maximumPerson) {
        this.title = title;
        this.id = id;
        this.content = content;
        this.picture = picture;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPerson = maximumPerson;
    }

    public void update(VolunteerUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.picture = requestDto.getPicture();
        this.volunteerDate = requestDto.getVolunteerDate();
        this.applicationDate = requestDto.getApplicationDate();
        this.maximumPerson = requestDto.getMaximumPerson();
    }
}
