package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Long volunteerDate;

    private Long applicationDate;

    private Integer maximumPeople;
    private Integer currentPeople;

    @Builder
    public Volunteer(Long id, String title, String content, String picture, Long volunteerDate, Long applicationDate, Integer maximumPeople) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.picture = picture;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPeople = maximumPeople;
        this.currentPeople = INITIAL_CURRENT_PERSON_VALUE;
    }

    public void update(VolunteerUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.picture = requestDto.getPicture();
        this.volunteerDate = requestDto.getVolunteerDate();
        this.applicationDate = requestDto.getApplicationDate();
        this.maximumPeople = requestDto.getMaximumPeople();
        this.currentPeople = requestDto.getCurrentPeople();
    }
}
