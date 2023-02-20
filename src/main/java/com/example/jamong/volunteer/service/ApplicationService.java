package com.example.jamong.volunteer.service;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.volunteer.domain.Application;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public List<Application> getApplicationsByVolunteer(Volunteer entity) {
        List<Application> applications = applicationRepository.findByVolunteer(entity);
        return applications;
    }

    public Application applyVolunteer(Application application) {
        applicationRepository.save(application);
        return application;
    }

    public void deleteByUserAndVolunteer(User user, Volunteer volunteer) {
        Application application = applicationRepository.findByUserAndVolunteer(user, volunteer);
        applicationRepository.delete(application);
    }

    public boolean isAppliedVolunteer(Long volunteerId, UserEmailRequestDto emailRequestDto) {
        return applicationRepository.existsByVolunteerIdAndUserEmail(volunteerId, emailRequestDto.getEmail());
    }
}
