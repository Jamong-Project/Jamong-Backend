package com.example.jamong.user;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.TokenRequestDto;
import com.example.jamong.user.repository.UserRepository;
import com.example.jamong.user.service.UserService;
import com.example.jamong.volunteer.repository.ApplyListRepository;
import com.example.jamong.volunteer.repository.FavoriteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class UserLoginTest {
    @Autowired
    StubUserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }
    @Test
    @DisplayName("토큰을 주면 유저 프로필 정보를 반환")
    public void getInfoTest() throws JsonProcessingException {
        ResponseEntity<User> info = userService.getProfile(new TokenRequestDto("fake Token"));

        assertThat(info.getBody().getNaverId()).isEqualTo("1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM");
        assertThat(info.getBody().getProfileImage()).isEqualTo("https://ssl.pstatic.net/static/pwe/address/img_profile.png");
        assertThat(info.getBody().getEmail()).isEqualTo("lmj938@naver.com");
        assertThat(info.getBody().getName()).isEqualTo("이민재");
    }

    @Test
    @DisplayName("이미 유저 정보가 있는 경우, 토큰을 주면 유저 프로필 정보를 반환")
    public void getAlreadyExistsInfoTest() throws JsonProcessingException {
        userService.getProfile(new TokenRequestDto("fake Token"));

        ResponseEntity<User> info = userService.getProfile(new TokenRequestDto("fake Token"));

        assertThat(info.getBody().getNaverId()).isEqualTo("1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM");
        assertThat(info.getBody().getProfileImage()).isEqualTo("https://ssl.pstatic.net/static/pwe/address/img_profile.png");
        assertThat(info.getBody().getEmail()).isEqualTo("lmj938@naver.com");
        assertThat(info.getBody().getName()).isEqualTo("이민재");
    }
}

@Service
class StubUserService extends UserService {

    public StubUserService(UserRepository userRepository, ApplyListRepository applyListRepository, FavoriteRepository favoriteRepository) {
        super(userRepository, applyListRepository, favoriteRepository);
    }

    @Override
    protected String getJsonUserProfile(TokenRequestDto tokenRequestDto) {
        return "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM\",\"profile_image\":\"https://ssl.pstatic.net/static/pwe/address/img_profile.png\",\"email\":\"lmj938@naver.com\",\"name\":\"이민재\"}}";
    }
}