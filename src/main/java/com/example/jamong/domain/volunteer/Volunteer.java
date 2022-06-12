package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.picture.Picture;
import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
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
public class Volunteer {
    private static final Integer INITIAL_CURRENT_PERSON_VALUE = 0;
    private final static Integer REPRESENTATIVE_IMAGE_INDEX = 0;

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

    private Integer maximumPeople;
    private Integer currentPeople;

    @Builder
    public Volunteer(String title, String content, List<Picture> pictures, Long volunteerDate, Long applicationDate, Integer maximumPeople) {
        this.title = title;
        this.content = content;
        this.pictures = pictures;
        this.volunteerDate = volunteerDate;
        this.applicationDate = applicationDate;
        this.maximumPeople = maximumPeople;
        this.currentPeople = INITIAL_CURRENT_PERSON_VALUE;
    }

    public Picture representPicture() {
        if (pictures.isEmpty() || pictures == null) {
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
