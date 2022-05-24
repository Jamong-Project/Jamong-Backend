package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.volunteer.dto.VolunteerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class VolunteerController {
    private final VolunteerService volunteerService;

    @GetMapping("/v1/volunteers")
    public List<VolunteerResponseDto> findAll() {
        return volunteerService.findAll();
    }

}
