package com.example.jamong.volunteer.facade;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.user.service.UserService;
import com.example.jamong.volunteer.domain.Application;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.service.ApplicationService;
import com.example.jamong.volunteer.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class VolunteerApplicationFacade {

    private final ApplicationService applicationService;
    private final VolunteerService volunteerService;
    private final UserService userService;

    @Transactional
    public void applyVolunteer(Long volunteerId, UserEmailRequestDto emailRequestDto) {
        User user = userService.getUserByEmail(emailRequestDto.getEmail());
        Volunteer volunteer = volunteerService.getVolunteerById(volunteerId);

        Application application = Application.builder()
                .volunteer(volunteer)
                .user(user)
                .build();

        if (!isAppliedVolunteer(volunteerId, emailRequestDto)) {
            volunteer.addApplication(application);
            applicationService.applyVolunteer(application);
            return;
        }

        volunteer.removeApplication(application);
        applicationService.deleteByUserAndVolunteer(user, volunteer);
        return;
    }

    @Transactional(readOnly = true)
    public boolean isAppliedVolunteer(Long volunteerId, UserEmailRequestDto emailRequestDto) {
        return applicationService.isAppliedVolunteer(volunteerId, emailRequestDto);
    }
}
