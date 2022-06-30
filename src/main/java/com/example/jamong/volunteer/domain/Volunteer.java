package com.example.jamong.volunteer.domain;

import com.example.jamong.config.BaseTimeEntity;
import com.example.jamong.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Volunteer extends BaseTimeEntity {
    private final static int INITIAL_CURRENT_PERSON_VALUE = 0;
    private final static int REPRESENTATIVE_IMAGE_INDEX = 0;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String content;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictures = new ArrayList<>();

    private Long volunteerDate;

    private Long applicationDate;

    private int maximumPeople;

    private int currentPeople;

    @Builder
    public Volunteer(String title, String content, List<Picture> pictures, Long volunteerDate, Long applicationDate, int maximumPeople, Integer currentPeople) {
        this.title = title;
        this.content = content;
        this.pictures = pictures;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPeople = maximumPeople;
        this.currentPeople = INITIAL_CURRENT_PERSON_VALUE;

        if (currentPeople != null) {
            this.currentPeople = currentPeople;
        }
    }

    public Picture representPicture() {
        if (pictures == null || pictures.isEmpty()) {
            return null;
        }
        return pictures.get(0);
    }

    public void update(VolunteerUpdateRequestDto requestDto) {
        if (requestDto.getTitle() != null) {
            this.title = requestDto.getTitle();
        }

        if (requestDto.getContent() != null) {
            this.content = requestDto.getContent();
        }

        if (requestDto.getPictures() != null) {
            this.pictures = requestDto.getPictures();
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
