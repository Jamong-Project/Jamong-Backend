package com.example.jamong.volunteer.repository;

import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.VolunteerSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class VolunteerRepositoryTest {
    @Autowired
    VolunteerRepository volunteerRepository;

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
    @DisplayName("모든 봉사를 조회한다.")
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
    @DisplayName("봉사를 등록한다.")
    public void postVolunteerTest() {
        String title = "테스트 봉사 제목";
        String content = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        Long volunteerDate = 1660112000000L;
        Long applicationDate = 1674121200000L;
        Integer maximumPeople = 20;

        Volunteer entity = Volunteer.builder()
                .title(title)
                .content(content)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .build();

        VolunteerSaveRequestDto savedVolunteer = VolunteerSaveRequestDto.builder()
                .entity(entity)
                .build();

        log.info(savedVolunteer.getTitle());

        volunteerRepository.save(savedVolunteer.toEntity());

        List<Volunteer> volunteerList = volunteerRepository.findAll();

        Volunteer volunteer = volunteerList.get(0);

        assertThat(volunteer.getTitle()).isEqualTo("테스트 봉사 제목");
        assertThat(volunteer.getContent()).isEqualTo("테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.");
        assertThat(volunteer.getVolunteerDate()).isEqualTo(1660112000000L);
        assertThat(volunteer.getApplicationDate()).isEqualTo(1674121200000L);
        assertThat(volunteer.getMaximumPeople()).isEqualTo(20);
        assertThat(volunteer.getCurrentPeople()).isEqualTo(0);
    }

    @Test
    @DisplayName("봉사를 삭제한다")
    public void deleteVolunteerTest() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);

        volunteerRepository.delete(volunteer);

        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList.size()).isEqualTo(0);
    }

}


