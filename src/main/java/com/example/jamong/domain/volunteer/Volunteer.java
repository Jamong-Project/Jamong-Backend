package com.example.jamong.domain.volunteer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static javax.persistence.GenerationType.*;

@Getter
@NoArgsConstructor
@Entity
public class Volunteer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String picture;
    private String dDay;
    private String applyDay;
    private String maximumPerson;

    @Builder
    public Volunteer(Long id,String title, String content, String picture, String dDay, String applyDay, String maximumPerson) {
        this.title = title;
        this.id = id;
        this.content = content;
        this.picture = picture;
        this.dDay = dDay;
        this.applyDay = applyDay;
        this.maximumPerson = maximumPerson;
    }
}
