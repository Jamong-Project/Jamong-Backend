package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Volunteer {
    private static final Integer INITIAL_CURRENT_PERSON_VALUE = 0;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String content;

    @Column(length = 1000)
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
        if (requestDto.getTitle() != null) {
            this.title = requestDto.getTitle();
        }

        if (requestDto.getContent() != null) {
            this.content = requestDto.getContent();
        }

        if (requestDto.getPicture() != null) {
            this.picture = requestDto.getPicture();
        }

        if (requestDto.getApplicationDate() != null) {
            this.applicationDate = requestDto.getApplicationDate();
        }

        if (requestDto.getVolunteerDate() != null) {
            this.volunteerDate = requestDto.getVolunteerDate();
        }

        if (requestDto.getMaximumPeople() != null) {
            this.maximumPeople = requestDto.getMaximumPeople();
        }

        if (requestDto.getCurrentPeople() != null) {
            this.currentPeople = requestDto.getCurrentPeople();
        }

    }
}
