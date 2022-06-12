package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.picture.Picture;
import com.example.jamong.domain.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class VolunteerTest {
    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    VolunteerService volunteerService;
}
