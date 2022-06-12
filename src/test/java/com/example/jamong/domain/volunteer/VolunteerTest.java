package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.picture.Picture;
import com.example.jamong.domain.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
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

    @Autowired
    VolunteerService volunteerService;

    String title = "테스트 봉사 제목";
    String content = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
    Long volunteerDate = 1660112000000L;
    Long applicationDate = 1674121200000L;
    Integer maximumPeople = 20;
    Integer currentPeople = 0;

    @AfterEach
    public void CleanUp() {
        volunteerRepository.deleteAll();
    }

    @BeforeEach
    public void makeDummyData() {
        Volunteer savedVolunteer = Volunteer.builder()
                .title(title)
                .content(content)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .build();

        volunteerRepository.save(savedVolunteer);
    }

    @Test
    public void getAllVolunteerTest() {
        List<Volunteer> volunteerList = volunteerRepository.findAll();

        Volunteer volunteer = volunteerList.get(0);

        assertThat(volunteer.getTitle()).isEqualTo(title);
        assertThat(volunteer.getVolunteerDate()).isEqualTo(volunteerDate);
        assertThat(volunteer.getApplicationDate()).isEqualTo(applicationDate);
        assertThat(volunteer.getMaximumPeople()).isEqualTo(maximumPeople);
        assertThat(volunteer.getCurrentPeople()).isEqualTo(currentPeople);

    }

    @Test
    public void postVolunteerTest() {
        String title = "테스트 봉사 제목";
        String content = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        String picture = "testImage";
        Long volunteerDate = 1660112000000L;
        Long applicationDate = 1674121200000L;
        Integer maximumPeople = 20;


        VolunteerSaveRequestDto savedVolunteer = VolunteerSaveRequestDto.builder()
                .title(title)
                .content(content)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .build();

        log.info(savedVolunteer.getTitle());

        volunteerRepository.save(savedVolunteer.toEntity());

        List<Volunteer> volunteerList = volunteerRepository.findAll();

        Volunteer volunteer = volunteerList.get(1);

        assertThat(volunteer.getTitle()).isEqualTo(title);
        assertThat(volunteer.getContent()).isEqualTo(content);
        assertThat(volunteer.getPictures()).isEqualTo(picture);
        assertThat(volunteer.getVolunteerDate()).isEqualTo(volunteerDate);
        assertThat(volunteer.getApplicationDate()).isEqualTo(applicationDate);
        assertThat(volunteer.getMaximumPeople()).isEqualTo(maximumPeople);
        assertThat(volunteer.getCurrentPeople()).isEqualTo(0);
    }

    @Test
    public void updateVolunteerTest() {
        String updatedTitle = "변경된 테스트 봉사 제목";
        String updatedContent = "변경된 테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        Long updatedVolunteerDate = 1660112000000L;
        Long updatedApplicationDate = 1674121200000L;
        Integer updatedMaximumPeople = 10;
        Integer updatedCurrentPeople = 2;

        VolunteerUpdateRequestDto requestDto = VolunteerUpdateRequestDto.builder()
                .title(updatedTitle)
                .content(updatedContent)
                .volunteerDate(updatedVolunteerDate)
                .applicationDate(updatedApplicationDate)
                .maximumPeople(updatedMaximumPeople)
                .currentPeople(updatedCurrentPeople)
                .build();

        List<Volunteer> volunteerList = volunteerRepository.findAll();

        Volunteer volunteer = volunteerList.get(0);

        volunteerService.update(volunteer.getId(), requestDto);

        assertThat(volunteer.getTitle()).isEqualTo(title);
        assertThat(volunteer.getContent()).isEqualTo(content);
        assertThat(volunteer.getVolunteerDate()).isEqualTo(volunteerDate);
        assertThat(volunteer.getApplicationDate()).isEqualTo(applicationDate);
        assertThat(volunteer.getMaximumPeople()).isEqualTo(maximumPeople);
        assertThat(volunteer.getCurrentPeople()).isEqualTo(currentPeople);
    }

    @Test
    public void deleteVolunteerTest() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);

        volunteerService.delete(volunteer.getId());

        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList.size()).isEqualTo(0);
    }
}
