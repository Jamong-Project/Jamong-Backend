package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.volunteer.dto.VolunteerSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class VolunteerTest {
    @Autowired
    VolunteerRepository volunteerRepository;


    @AfterEach
    public void CleanUp() {
        volunteerRepository.deleteAll();
    }

    @BeforeEach
    public void makeDummyData() {


    }

    @Test
    public void getAllVolunteerTest() {
        String title = "테스트 봉사 제목";
        String picture = "testImage";
        String volunteerDate = "2022-05-24";
        String applicationDate = "2022-05-25 18:00";
        String maximumPerson = "20";

        Volunteer savedVolunteer = Volunteer.builder()
                .title(title)
                .picture(picture)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPerson(maximumPerson)
                .build();
        log.info(savedVolunteer.getTitle());

        volunteerRepository.save(savedVolunteer);

        List<Volunteer> volunteerList = volunteerRepository.findAll();

        Volunteer volunteer = volunteerList.get(0);

        assertThat(volunteer.getTitle()).isEqualTo(title);
        assertThat(volunteer.getPicture()).isEqualTo(picture);
        assertThat(volunteer.getVolunteerDate()).isEqualTo(volunteerDate);
        assertThat(volunteer.getApplicationDate()).isEqualTo(applicationDate);
        assertThat(volunteer.getMaximumPerson()).isEqualTo(maximumPerson);

    }

    @Test
    public void postVolunteerTest() {
        String title = "테스트 봉사 제목";
        String content = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        String picture = "testImage";
        String volunteerDate = "2022-05-24";
        String applicationDate = "2022-05-25 18:00";
        String maximumPerson = "20";

        VolunteerSaveRequestDto savedVolunteer = VolunteerSaveRequestDto.builder()
                .title(title)
                .content(content)
                .picture(picture)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPerson(maximumPerson)
                .build();

        log.info(savedVolunteer.getTitle());

        volunteerRepository.save(savedVolunteer.toEntity());

        List<Volunteer> volunteerList = volunteerRepository.findAll();

        Volunteer volunteer = volunteerList.get(0);

        assertThat(volunteer.getTitle()).isEqualTo(title);
        assertThat(volunteer.getContent()).isEqualTo(content);
        assertThat(volunteer.getPicture()).isEqualTo(picture);
        assertThat(volunteer.getVolunteerDate()).isEqualTo(volunteerDate);
        assertThat(volunteer.getApplicationDate()).isEqualTo(applicationDate);
        assertThat(volunteer.getMaximumPerson()).isEqualTo(maximumPerson);
    }

}