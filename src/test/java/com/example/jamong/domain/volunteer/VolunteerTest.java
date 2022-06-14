package com.example.jamong.domain.volunteer;

import com.example.jamong.volunteer.repository.VolunteerRepository;
import com.example.jamong.volunteer.service.VolunteerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class VolunteerTest {
    @LocalServerPort
    private int port;

    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;


}
