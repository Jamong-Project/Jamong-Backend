package com.example.jamong.volunteer.service;

import com.example.jamong.exception.NoExistVolunteerException;
import com.example.jamong.user.domain.Role;
import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.user.repository.UserRepository;
import com.example.jamong.volunteer.domain.Comment;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.*;
import com.example.jamong.volunteer.repository.ApplyListRepository;
import com.example.jamong.volunteer.repository.CommentRepository;
import com.example.jamong.volunteer.repository.FavoriteRepository;
import com.example.jamong.volunteer.repository.VolunteerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VolunteerServiceTest {
    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplyListRepository applyListRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private CommentRepository commentRepository;

    @AfterEach
    public void CleanUp() {
        volunteerRepository.deleteAll();
    }

    @BeforeAll
    public void setUp() {
        String naverId = "1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM";
        String profileImage = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";
        String email = "lmj938@naver.com";
        String name = "이민재";
        Role role = Role.GUEST;

        userRepository.save(
                User.builder()
                        .naverId(naverId)
                        .profileImage(profileImage)
                        .email(email)
                        .name(name)
                        .role(role)
                        .build()
        );
    }

    @BeforeEach
    public void makeDummyData() {
        String title = "테스트 봉사 제목";
        String content = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        Long volunteerDate = 1660112000000L;
        Long applicationDate = 1674121200000L;
        Integer maximumPeople = 20;
        Integer currentPeople = 0;

        for (int i = 1; i < 51; i++) {
            volunteerRepository.save(
                    Volunteer.builder()
                            .title(title + i)
                            .content(content + i)
                            .volunteerDate(volunteerDate)
                            .applicationDate(applicationDate)
                            .maximumPeople(maximumPeople)
                            .build()
            );
        }

    }

    @Test
    @DisplayName("모든 봉사를 조회한다")
    void findAll() {
        Pageable paging = PageRequest.of(0, 12);
        ResponseEntity<List<VolunteerCardResponseDto>> volunteerResponseEntity = volunteerService.findAll(paging);

        VolunteerCardResponseDto actualVolunteer = volunteerResponseEntity.getBody().get(0);

        assertThat(volunteerResponseEntity.getBody().size()).isEqualTo(12);
        assertThat(actualVolunteer.getTitle()).isEqualTo("테스트 봉사 제목1");
        assertThat(actualVolunteer.getVolunteerDate()).isEqualTo(1660112000000L);
        assertThat(actualVolunteer.getApplicationDate()).isEqualTo(1674121200000L);
        assertThat(actualVolunteer.getMaximumPeople()).isEqualTo(20);
        assertThat(actualVolunteer.getCurrentPeople()).isEqualTo(0);
    }

    @Test
    @DisplayName("특정 봉사를 조회한다.")
    void findById() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);
        VolunteerArticleResponseDto actualVolunteer = volunteerService.findById(volunteer.getId());

        assertThat(actualVolunteer.getTitle()).isEqualTo("테스트 봉사 제목1");
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

        Volunteer actualVolunteer = volunteerRepository.findAll().get(50);

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
        assertThat(volunteerList.size()).isEqualTo(49);
    }

    @Test
    @DisplayName("없는 게시물 조회 시 예외 발생")
    public void noExistVolunteerExceptionTest() {
        assertThatExceptionOfType(NoExistVolunteerException.class)
                .isThrownBy(
                        () -> {
                            volunteerService.findById(55L);
                        });
    }

    @Test
    @DisplayName("유저가 봉사를 신청한다.")
    void addUser() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);

        UserEmailRequestDto userEmailRequestDto = UserEmailRequestDto.builder()
                .email(user.getEmail())
                .build();

        volunteerService.isApplyVolunteer(volunteer.getId(), userEmailRequestDto);

        Volunteer updatedVolunteer = volunteerRepository.findById(volunteer.getId()).get();

        User applyUser = applyListRepository.findByVolunteer(updatedVolunteer).get(0).getUser();

        assertThat(updatedVolunteer.getCurrentPeople()).isEqualTo(1);
        assertThat(applyUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("유저가 좋아요를 누른다")
    void pressFavoriteTest() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);

        UserEmailRequestDto userEmailRequestDto = UserEmailRequestDto.builder()
                .email(user.getEmail())
                .build();

        volunteerService.isPressFavorite(volunteer.getId(), userEmailRequestDto);

        Volunteer updatedVolunteer = volunteerRepository.findById(volunteer.getId()).get();

        User applyUser = favoriteRepository.findByVolunteer(updatedVolunteer).get(0).getUser();

        assertThat(applyUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("유저가 봉사를 신청하지 않은 상태에서는 봉사가 신청되고, 아니면 취소된다.")
    void cancelApplyTest() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);

        UserEmailRequestDto userEmailRequestDto = UserEmailRequestDto.builder()
                .email(user.getEmail())
                .build();

        volunteerService.isApplyVolunteer(volunteer.getId(), userEmailRequestDto);

        Volunteer updatedVolunteer = volunteerRepository.findById(volunteer.getId()).get();

        User applyUser = applyListRepository.findByVolunteer(updatedVolunteer).get(0).getUser();

        assertThat(updatedVolunteer.getCurrentPeople()).isEqualTo(1);
        assertThat(applyUser.getEmail()).isEqualTo(user.getEmail()); //신청

        volunteerService.isApplyVolunteer(volunteer.getId(), userEmailRequestDto);
        updatedVolunteer = volunteerRepository.findById(volunteer.getId()).get();

        assertThat(updatedVolunteer.getCurrentPeople()).isEqualTo(0);
        assertThat(applyListRepository.findByVolunteer(updatedVolunteer).size()).isEqualTo(0); // 취소
    }

    @Test
    @DisplayName("유저가 좋아요를 누른 상태에서는 좋아요가 눌리고, 아니면 취소된다.")
    void cancelFavorite() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);

        UserEmailRequestDto userEmailRequestDto = UserEmailRequestDto.builder()
                .email(user.getEmail())
                .build();

        volunteerService.isPressFavorite(volunteer.getId(), userEmailRequestDto);
        Volunteer updatedVolunteer = volunteerRepository.findById(volunteer.getId()).get();
        User pressUser = favoriteRepository.findByUserAndVolunteer(user, updatedVolunteer).get(0).getUser();
        assertThat(pressUser.getEmail()).isEqualTo(user.getEmail()); //신청

        volunteerService.isPressFavorite(volunteer.getId(), userEmailRequestDto);
        updatedVolunteer = volunteerRepository.findById(volunteer.getId()).get();
        List<Favorite> pressTwiceUser = favoriteRepository.findByUserAndVolunteer(user, updatedVolunteer);
        assertThat(pressTwiceUser.size()).isEqualTo(0); // 취소
    }

    @Test
    @DisplayName("유저가 댓글을 등록한다.")
    void addCommentTest() {
        Volunteer volunteer = volunteerRepository.findAll().get(0);
        User user = userRepository.findAll().get(0);

        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("댓글")
                .email("lmj938@naver.com")
                .build();

        volunteerService.addComment(volunteer.getId(), commentRequestDto);

        Volunteer updatedVolunteer = volunteerRepository.findById(volunteer.getId()).get();
        Comment comment = commentRepository.findByVolunteer(updatedVolunteer).get(0);
        assertThat(comment.getUser().getEmail()).isEqualTo(user.getEmail());
        assertThat(comment.getContent()).isEqualTo("댓글");
    }
}