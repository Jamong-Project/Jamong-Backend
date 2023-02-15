package com.example.jamong.volunteer.service;

import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.VolunteerCardResponseDto;
import com.example.jamong.volunteer.repository.VolunteerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class VolunteerServiceTest {

    @InjectMocks
    VolunteerService volunteerService;

    @Mock
    VolunteerRepository volunteerRepository;

    @Test
    @DisplayName("봉사활동 전체 조회")
    public void findAll() {
        Volunteer volunteer1 = Volunteer.builder()
                .title("봉사활동")
                .content("봉사활동 내용")
                .build();

        Volunteer volunteer2 = Volunteer.builder()
                .title("봉사활동")
                .content("봉사활동 내용")
                .build();

        List<Volunteer> volunteers = new ArrayList<>();

        volunteers.add(volunteer1);
        volunteers.add(volunteer2);

        List<VolunteerCardResponseDto> volunteerCardResponseDtoList = volunteers.stream()
                .map(Volunteer::toCardDto)
                .collect(Collectors.toList());

        Page<Volunteer> page = new PageImpl(volunteers);

        given(volunteerRepository.findAll(any(Pageable.class))).willReturn(page);

        Pageable pageable = PageRequest.of(0, 2);
        assertThat(volunteerService.findAll(pageable)).usingRecursiveComparison().isEqualTo(volunteerCardResponseDtoList);
    }

    @Test
    @DisplayName("전체 페이지 개수를 올바르게 가져오는지 확인한다.")
    public void getTotalPage() {
        Volunteer volunteer1 = Volunteer.builder()
                .title("봉사활동")
                .content("봉사활동 내용")
                .build();

        Volunteer volunteer2 = Volunteer.builder()
                .title("봉사활동")
                .content("봉사활동 내용")
                .build();

        List<Volunteer> volunteers = new ArrayList<>();

        volunteers.add(volunteer1);
        volunteers.add(volunteer2);

        given(volunteerRepository.findAll()).willReturn(volunteers);

        HttpHeaders totalPage = volunteerService.getTotalPage();
        assertThat(totalPage.get("total-Page").get(0)).isEqualTo("2");
    }

    @Test
    @DisplayName("봉사활동 상세 조회")
    public void getVolunteerById () {
        long id = 1L;
        Volunteer volunteer = Volunteer.builder()
                .title("봉사활동")
                .content("봉사활동 내용")
                .build();

        given(volunteerRepository.findById(id)).willReturn(ofNullable(volunteer));
        assertThat(volunteerService.getVolunteerById(id)).usingRecursiveComparison().isEqualTo(volunteer);
    }


    @Test
    @DisplayName("봉사활동 신청 테스트")
    public void isApplyVolunteer() {
    }
}
