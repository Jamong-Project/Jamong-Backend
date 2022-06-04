package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

@Getter
@NoArgsConstructor
@Entity
public class Volunteer {
    private static final Integer INITIAL_CURRENT_PERSON_VALUE = 0;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String picture;

    private LocalDateTime volunteerDate;

    private LocalDateTime applicationDate;

    private Integer maximumPerson;
    private Integer currentPerson;

    @Builder
    public Volunteer(Long id, String title, String content, String picture, LocalDateTime volunteerDate, LocalDateTime applicationDate, Integer maximumPerson, Integer currentPerson) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPerson = maximumPerson;
        this.currentPerson = INITIAL_CURRENT_PERSON_VALUE;
    }

    public void update(VolunteerUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.picture = requestDto.getPicture();
        this.volunteerDate = requestDto.getVolunteerDate();
        this.applicationDate = requestDto.getApplicationDate();
        this.maximumPerson = requestDto.getMaximumPerson();
        this.currentPerson = requestDto.getCurrentPerson();
    }
}
