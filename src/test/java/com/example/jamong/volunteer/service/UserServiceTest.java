package com.example.jamong.volunteer.service;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.NaverResponseDto;
import com.example.jamong.user.dto.TokenRequestDto;
import com.example.jamong.user.dto.UserSaveRequestDto;
import com.example.jamong.user.repository.UserRepository;
import com.example.jamong.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class UserServiceTest {
    @Autowired
    StubUserService userService;

    @Test
    @DisplayName("토큰을 주면 유저 프로필 정보를 반환")
    public void getInfoTest() throws JsonProcessingException {
        User info = userService.getProfile(new TokenRequestDto("fake Token"));

        assertThat(info.getNaverId()).isEqualTo("1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM");
        assertThat(info.getProfileImage()).isEqualTo("https://ssl.pstatic.net/static/pwe/address/img_profile.png");
        assertThat(info.getGender()).isEqualTo("M");
        assertThat(info.getEmail()).isEqualTo("lmj938@naver.com");
        assertThat(info.getMobile()).isEqualTo("010-5913-7109");
        assertThat(info.getMobileE164()).isEqualTo("+821059137109");
        assertThat(info.getName()).isEqualTo("이민재");
    }
}

@Service
class StubUserService extends UserService {

    public StubUserService(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    protected String getJsonUserProfile(TokenRequestDto tokenRequestDto) {
        return "{\"resultcode\":\"00\",\"message\":\"success\",\"response\":{\"id\":\"1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM\",\"profile_image\":\"https://ssl.pstatic.net/static/pwe/address/img_profile.png\",\"gender\":\"M\",\"email\":\"lmj938@naver.com\",\"mobile\":\"010-5913-7109\",\"mobile_e164\":\"+821059137109\",\"name\":\"이민재\"}}";
    }
}