package com.example.jamong.volunteer.controller;

import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.volunteer.repository.VolunteerRepository;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VolunteerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        volunteerRepository.deleteAll();

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

    @AfterEach
    public void teadDown() {
        volunteerRepository.deleteAll();
    }

    @Test
    @DisplayName("봉사 GET 요청")
    public void volunteerGetTest() throws Exception {
        String url = "http://localhost:" + port + "/v1/volunteers";

        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("봉사 POST 요청")
    public void volunteerPostTest() throws Exception {
        String title = "테스트 봉사 제목";
        String content = "테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        Long volunteerDate = 1660112000000L;
        Long applicationDate = 1674121200000L;
        Integer maximumPeople = 20;
        Integer currentPeople = 0;

        VolunteerSaveRequestDto saved = VolunteerSaveRequestDto.builder()
                .title(title)
                .content(content)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .build();

        String url = "http://localhost:" + port + "/v1/volunteers";

        String request = new ObjectMapper().writeValueAsString(saved);
        MockMultipartFile json = new MockMultipartFile("request", "jsondata", "application/json", request.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile image = new MockMultipartFile("file-data", "filename-1.jpeg", "image/jpeg", "<<jpeg data>>".getBytes());

        mvc.perform(multipart(url)
                        .file(json)
                        .file(image)
                        .contentType("application/json")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("봉사 PATCH 요청")
    public void volunteerPatchTest() throws Exception {
        Volunteer volunteer = volunteerRepository.findAll().get(0);

        String title = "변경된 테스트 봉사 제목";
        String content = "변경된 테스트 봉사 내용, 이번 봉사는 한강 플로깅 봉사입니다.";
        Long volunteerDate = 1660112000001L;
        Long applicationDate = 1674121200001L;
        Integer maximumPeople = 10;
        Integer currentPeople = 0;

        VolunteerSaveRequestDto updated = VolunteerSaveRequestDto.builder()
                .title(title)
                .content(content)
                .volunteerDate(volunteerDate)
                .applicationDate(applicationDate)
                .maximumPeople(maximumPeople)
                .build();

        String url = "http://localhost:" + port + "/v1/volunteers/" + volunteer.getId();

        String request = new ObjectMapper().writeValueAsString(updated);

        mvc.perform(patch(url)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("봉사 DELETE 요청")
    public void volunteerDeleteTest() throws Exception {
        Volunteer volunteer = volunteerRepository.findAll().get(0);

        String url = "http://localhost:" + port + "/v1/volunteers/" + volunteer.getId();

        mvc.perform(delete(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

