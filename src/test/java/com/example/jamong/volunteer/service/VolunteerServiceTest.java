package com.example.jamong.volunteer.service;

import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.VolunteerArticleDto;
import com.example.jamong.volunteer.dto.VolunteerCardDto;
import com.example.jamong.volunteer.dto.VolunteerUpdateRequestDto;
import com.example.jamong.volunteer.repository.VolunteerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class VolunteerServiceTest {
    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @AfterEach
    public void CleanUp() {
        volunteerRepository.deleteAll();
    }

    @BeforeEach
    public void makeDummyData() {
        String title = "테스트 봉사 제목";
        String content = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        Long volunteerDate = 1660112000000L;
        Long applicationDate = 1674121200000L;
        Integer maximumPeople = 20;
        Integer currentPeople = 0;

        Volunteer savedVolunteer = Volunteer.builder()
                .title(title)
                .content(content)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .build();

        String title2 = "테스트 봉사 제목2";
        String content2 = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.2";
        Long volunteerDate2 = 1660112000000L;
        Long applicationDate2 = 1674121200000L;
        Integer maximumPeople2 = 20;
        Integer currentPeople2 = 0;

        Volunteer savedVolunteer2 = Volunteer.builder()
                .title(title2)
                .content(content2)
                .volunteerDate(volunteerDate2)
                .applicationDate(applicationDate2)
                .maximumPeople(maximumPeople2)
                .build();

        volunteerRepository.save(savedVolunteer);
        volunteerRepository.save(savedVolunteer2);
    }

    @Test
    @DisplayName("모든 봉사를 조회한다")
    void findAll() {
        ResponseEntity<List<VolunteerCardDto>> volunteerResponseEntity = volunteerService.findAll(null, null, null);

        VolunteerCardDto actualVolunteer = volunteerResponseEntity.getBody().get(0);

        assertThat(volunteerResponseEntity.getBody().size()).isEqualTo(2);
        assertThat(actualVolunteer.getTitle()).isEqualTo("테스트 봉사 제목");
        assertThat(actualVolunteer.getVolunteerDate()).isEqualTo(1660112000000L);
        assertThat(actualVolunteer.getApplicationDate()).isEqualTo(1674121200000L);
        assertThat(actualVolunteer.getMaximumPeople()).isEqualTo(20);
        assertThat(actualVolunteer.getCurrentPeople()).isEqualTo(0);
    }

    @Test
    @DisplayName("특정 봉사를 조회한다.")
    void findById() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);
        VolunteerArticleDto actualVolunteer = volunteerService.findById(volunteer.getId());

        assertThat(actualVolunteer.getTitle()).isEqualTo("테스트 봉사 제목");
        assertThat(actualVolunteer.getVolunteerDate()).isEqualTo(1660112000000L);
        assertThat(actualVolunteer.getApplicationDate()).isEqualTo(1674121200000L);
        assertThat(actualVolunteer.getMaximumPeople()).isEqualTo(20);
        assertThat(actualVolunteer.getCurrentPeople()).isEqualTo(0);
    }

    @Test
    @DisplayName("봉사를 저장한다.")
    void save() {
        String title = "새로 저장된 테스트 봉사 제목";
        String content = "새로 저장된 테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        Long volunteerDate = 1660112000100L;
        Long applicationDate = 1674121201000L;
        Integer maximumPeople = 15;

        Volunteer savedVolunteer = Volunteer.builder()
                .title(title)
                .content(content)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .build();

        volunteerRepository.save(savedVolunteer);

        Volunteer actualVolunteer = volunteerRepository.findAll().get(2);

        assertThat(actualVolunteer.getTitle()).isEqualTo("새로 저장된 테스트 봉사 제목");
        assertThat(actualVolunteer.getContent()).isEqualTo("새로 저장된 테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.");
        assertThat(actualVolunteer.getVolunteerDate()).isEqualTo(1660112000100L);
        assertThat(actualVolunteer.getApplicationDate()).isEqualTo(1674121201000L);
        assertThat(actualVolunteer.getMaximumPeople()).isEqualTo(15);
        assertThat(actualVolunteer.getCurrentPeople()).isEqualTo(0);
    }

    @Test
    @DisplayName("봉사를 수정한다")
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

        Volunteer volunteer = volunteerRepository.findAll().get(0);
        Volunteer updatedVolunteer = volunteerService.update(volunteer.getId(), requestDto);

        assertThat(updatedVolunteer.getTitle()).isEqualTo("변경된 테스트 봉사 제목");
        assertThat(updatedVolunteer.getContent()).isEqualTo("변경된 테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.");
        assertThat(updatedVolunteer.getVolunteerDate()).isEqualTo(1660112000000L);
        assertThat(updatedVolunteer.getApplicationDate()).isEqualTo(1674121200000L);
        assertThat(updatedVolunteer.getMaximumPeople()).isEqualTo(10);
        assertThat(updatedVolunteer.getCurrentPeople()).isEqualTo(2);
    }

    @Test
    @DisplayName("봉사를 삭제한다")
    public void deleteVolunteerTest() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);

        volunteerService.delete(volunteer.getId());

        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList.size()).isEqualTo(1);
    }
}