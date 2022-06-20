package com.example.jamong.volunteer.domain;

import com.example.jamong.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Picture extends BaseTimeEntity {

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