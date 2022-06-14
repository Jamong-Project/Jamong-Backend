package com.example.jamong;

import com.example.jamong.volunteer.service.VolunteerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JamongApplicationTests {

	@MockBean
	protected VolunteerService volunteerService;
}
