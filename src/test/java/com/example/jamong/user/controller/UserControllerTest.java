package com.example.jamong.user.controller;

import com.example.jamong.user.domain.Role;
import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserUpdateRequestDto;
import com.example.jamong.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

        @LocalServerPort
        private int port;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private WebApplicationContext context;

        private MockMvc mvc;

        @BeforeEach
        public void setUp() {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .build();

            String naverId = "1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM";
            String profileImage = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";
            String gender = "M";
            String email = "lmj938@naver.com";
            String mobile = "010-0000-0000";
            String mobileE164 = "+821000000000";
            String name = "이민재";
            Role role = Role.GUEST;

            for (int i = 1; i < 51; i++) {
                userRepository.save(
                        User.builder()
                                .naverId(naverId + i)
                                .profileImage(profileImage + i)
                                .gender(gender)
                                .email(email + i)
                                .mobile(mobile + i)
                                .mobileE164(mobileE164 + i)
                                .name(name + i)
                                .role(role)
                                .build()
                );
            }
        }

        @AfterEach
        public void tearDown() {
            userRepository.deleteAll();
        }

        @Test
        @DisplayName("유저 GET 요청")
        public void volunteerGetTest() throws Exception {
            String url = "http://localhost:" + port + "/v1/users";

            mvc.perform(get(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(MockMvcResultMatchers.status().isOk());

        }

        @Test
        @DisplayName("유저 PATCH 요청")
        public void volunteerPatchTest() throws Exception {
            User user = userRepository.findAll().get(0);

            String profileImage = "변경된 https://ssl.pstatic.net/static/pwe/address/img_profile.png";
            String email = "lmj938@naver.com";
            String mobile = "010-0000-0000";

            UserUpdateRequestDto updated = UserUpdateRequestDto.builder()
                    .profileImage(profileImage)
                    .email(email)
                    .mobile(mobile)
                    .role(Role.ADMIN)
                    .cardinalNumber("32")
                    .build();

            String url = "http://localhost:" + port + "/v1/users/" + user.getId();

            ObjectMapper objectMapper = new ObjectMapper();

            mvc.perform(patch(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updated)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("유져 DELETE 요청")
        public void volunteerDeleteTest() throws Exception {
        }
}
