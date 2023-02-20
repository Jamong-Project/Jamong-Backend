package com.example.jamong.volunteer.domain;

import com.example.jamong.config.BaseTimeEntity;
import com.example.jamong.volunteer.dto.VolunteerCardResponseDto;
import com.example.jamong.volunteer.dto.VolunteerUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictures = new ArrayList<>();

    private Long volunteerDate;

    private Long applicationDate;

    private int maximumPeople;

    private int currentPeople;

    @JsonBackReference
    @OneToMany(mappedBy = "volunteer")
    private List<Application> applications = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

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

    public List<Picture> getDetailPicture() {
        if (pictures == null || pictures.isEmpty()) {
            return null;
        }

        List<Picture> detailPictures = new ArrayList<>();

        for (int i = 0; i < pictures.size(); i++) {
            if (i % 2 != 0) {
                detailPictures.add(pictures.get(i));
            }
        }
        return detailPictures;
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

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public VolunteerCardResponseDto toCardDto() {
        return VolunteerCardResponseDto.builder()
                .entity(this)
                .build();
    }

    public void addApplication(Application application) {
        applications.add(application);
        currentPeople = applications.size();
    }

    public void removeApplication(Application application) {
        applications.remove(application);
        currentPeople = applications.size();
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }

    public void removeFavorite(Favorite favorite) {
        favorites.remove(favorite);
    }
}
