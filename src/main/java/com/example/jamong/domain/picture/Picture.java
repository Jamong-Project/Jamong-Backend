package com.example.jamong.domain.picture;

import com.example.jamong.domain.volunteer.Volunteer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String url;
    private String fileName;

    @Builder
    public Picture(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }
}