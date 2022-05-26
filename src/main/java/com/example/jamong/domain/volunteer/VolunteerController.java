package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.volunteer.dto.VolunteerResponseDto;
import com.example.jamong.domain.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class VolunteerController {
    private final VolunteerService volunteerService;

    @GetMapping("/v1/volunteers")
    public List<VolunteerResponseDto> findAll() {
        return volunteerService.findAll();
    }

    @PostMapping("/v1/volunteers")
    public Volunteer save(@RequestBody VolunteerSaveRequestDto requestDto) {
        return volunteerService.save(requestDto);
    }

    @PatchMapping("/v1/volunteers/{id}")
    public Volunteer update(@PathVariable Long id, @RequestBody VolunteerUpdateRequestDto requestDto) {
        return volunteerService.update(id, requestDto);
    }


}
