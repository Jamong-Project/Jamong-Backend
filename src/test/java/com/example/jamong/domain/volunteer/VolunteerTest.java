package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class VolunteerTest {
    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    VolunteerService volunteerService;

    String title = "테스트 봉사 제목";
    String content = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
    String picture = "이미지 testImage";
    LocalDateTime volunteerDate = LocalDateTime.parse("2022-05-24T00:00");
    LocalDateTime applicationDate = LocalDateTime.parse("2022-05-25T18:00");
    Integer maximumPerson = 20;
    Integer currentPerson = 0;

    @AfterEach
    public void CleanUp() {
        volunteerRepository.deleteAll();
    }

    @BeforeEach
    public void makeDummyData() {
        Volunteer savedVolunteer = Volunteer.builder()
                .title(title)
                .content(content)
                .picture(picture)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPerson(maximumPerson)
                .build();

        volunteerRepository.save(savedVolunteer);
    }

    @Test
    public void getAllVolunteerTest() {
        List<Volunteer> volunteerList = volunteerRepository.findAll();

        Volunteer volunteer = volunteerList.get(0);

        assertThat(volunteer.getTitle()).isEqualTo(title);
        assertThat(volunteer.getPicture()).isEqualTo(picture);
        assertThat(volunteer.getVolunteerDate()).isEqualTo(volunteerDate);
        assertThat(volunteer.getApplicationDate()).isEqualTo(applicationDate);
        assertThat(volunteer.getMaximumPerson()).isEqualTo(maximumPerson);
        assertThat(volunteer.getCurrentPerson()).isEqualTo(currentPerson);

    }

    @Test
    public void postVolunteerTest() {
        String title = "테스트 봉사 제목";
        String content = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        String picture = "testImage";
        LocalDateTime volunteerDate = LocalDateTime.parse("2022-05-24T00:00");
        LocalDateTime applicationDate = LocalDateTime.parse("2022-05-25T18:00");
        Integer maximumPerson = 20;


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

        Volunteer volunteer = volunteerList.get(1);

        assertThat(volunteer.getTitle()).isEqualTo(title);
        assertThat(volunteer.getContent()).isEqualTo(content);
        assertThat(volunteer.getPicture()).isEqualTo(picture);
        assertThat(volunteer.getVolunteerDate()).isEqualTo(volunteerDate);
        assertThat(volunteer.getApplicationDate()).isEqualTo(applicationDate);
        assertThat(volunteer.getMaximumPerson()).isEqualTo(maximumPerson);
        assertThat(volunteer.getCurrentPerson()).isEqualTo(0);
    }

    @Test
    public void updateVolunteerTest() {
        String updatedTitle = "변경된 테스트 봉사 제목";
        String updatedContent = "변경된 테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        String updatedPicture = "변경된 이미지 testImage";
        LocalDateTime updatedVolunteerDate = LocalDateTime.parse("2022-06-01T00:00");
        LocalDateTime updatedApplicationDate = LocalDateTime.parse("2022-06-01T18:00");
        Integer updatedMaximumPerson = 10;
        Integer updatedCurrentPerson = 3;

        VolunteerUpdateRequestDto requestDto = VolunteerUpdateRequestDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .picture(updatedPicture)
                .volunteerDate(updatedVolunteerDate)
                .applicationDate(updatedApplicationDate)
                .maximumPerson(updatedMaximumPerson)
                .currentPerson(updatedCurrentPerson)
                .build();

        List<Volunteer> volunteerList = volunteerRepository.findAll();

        Volunteer volunteer = volunteerList.get(0);

        volunteerService.update(volunteer.getId(), requestDto);

        assertThat(volunteer.getTitle()).isEqualTo(title);
        assertThat(volunteer.getContent()).isEqualTo(content);
        assertThat(volunteer.getPicture()).isEqualTo(picture);
        assertThat(volunteer.getVolunteerDate()).isEqualTo(volunteerDate);
        assertThat(volunteer.getApplicationDate()).isEqualTo(applicationDate);
        assertThat(volunteer.getMaximumPerson()).isEqualTo(maximumPerson);
        assertThat(volunteer.getCurrentPerson()).isEqualTo(currentPerson);
    }

    @Test
    public void deleteVolunteerTest() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);

        volunteerService.delete(volunteer.getId());

        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList.size()).isEqualTo(0);
    }
}