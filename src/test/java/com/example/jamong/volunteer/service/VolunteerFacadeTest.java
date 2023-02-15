package com.example.jamong.volunteer.service;

import com.example.jamong.user.domain.Role;
import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.user.service.UserService;
import com.example.jamong.volunteer.domain.Application;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.VolunteerCardResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VolunteerFacadeTest {

    @InjectMocks
    private VolunteerFacade volunteerFacade;

    @Mock
    private VolunteerService volunteerService;

    @Mock
    private UserService userService;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private FavoriteService favoriteService;

    private Volunteer firstVolunteer;
    private Volunteer secondVolunteer;
    private Volunteer volunteer;
    private List<VolunteerCardResponseDto> volunteerCardResponseDtoList;
    private User user;
    private UserEmailRequestDto userEmailRequestDto;
    private Application application;
    private Favorite favorite;

    @BeforeEach
    public void setUp() {
        firstVolunteer = Volunteer.builder()
                .title("봉사활동")
                .content("봉사활동 내용")
                .build();

        secondVolunteer = Volunteer.builder()
                .title("봉사활동")
                .content("봉사활동 내용")
                .build();

        volunteer = Volunteer.builder()
                .title("봉사활동")
                .content("봉사활동 내용")
                .build();

        List<Volunteer> volunteers = new ArrayList<>();

        volunteers.add(firstVolunteer);
        volunteers.add(secondVolunteer);

        volunteerCardResponseDtoList = volunteers.stream()
                .map(Volunteer::toCardDto)
                .collect(Collectors.toList());

        userEmailRequestDto = UserEmailRequestDto.builder()
                .email("lkj938@naver.com")
                .build();

        user = User.builder()
                .id(1L)
                .email("lkj938@naver.com")
                .cardinalNumber("34")
                .naverId("lkj938")
                .profileImage("https://ssl.pstatic.net/static/pwe/address/nodata_33x33.gif")
                .role(Role.GUEST)
                .build();

        application = Application.builder()
                .user(user)
                .volunteer(firstVolunteer)
                .build();

        favorite = Favorite.builder()
                .user(user)
                .volunteer(volunteer)
                .build();
    }

    @Test
    @DisplayName("봉사활동 카드 전체 조회")
    public void findAll() {
        //given
        given(volunteerService.findAll(any())).willReturn(volunteerCardResponseDtoList);

        //when
        Pageable page = PageRequest.of(0, 1);
        volunteerFacade.getVolunteerCards(page);
    }

    @Test
    @DisplayName("봉사를 신청한적이 없다면, 봉사활동 신청")
    public void apply() {
        //given
        volunteer.addApplication(application);

        given(userService.getUserByEmail(any())).willReturn(null);
        given(volunteerService.getVolunteerById(any())).willReturn(volunteer);
        given(applicationService.applyVolunteer(any())).willReturn(application);
        given(applicationService.isAppliedVolunteer(any(), any())).willReturn(false);

        //when
        volunteerFacade.applyVolunteer(1L, userEmailRequestDto);
        verify(applicationService).applyVolunteer(any());
    }

    @Test
    @DisplayName("봉사를 신청한적이 있다면, 봉사활동 취소")
    public void cancel() {
        //given
        volunteer.addApplication(application);

        given(userService.getUserByEmail(any())).willReturn(user);
        given(volunteerService.getVolunteerById(any())).willReturn(volunteer);
        given(applicationService.isAppliedVolunteer(any(), any())).willReturn(true);

        //when
        volunteerFacade.applyVolunteer(1L, userEmailRequestDto);
        verify(applicationService).deleteByUserAndVolunteer(any(), any());
    }

    @Test
    @DisplayName("좋아요를 누른적이 없다면, 좋아요 누르기")
    public void pressFavorite() {
        volunteer.addFavorite(favorite);

        given(volunteerService.getVolunteerById(any())).willReturn(volunteer);

        volunteerFacade.pressFavorite(1L, userEmailRequestDto);
        verify(favoriteService).save(any());
    }
}
