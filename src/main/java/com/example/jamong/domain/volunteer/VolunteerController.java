package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.s3.AwsS3Service;
import com.example.jamong.domain.volunteer.dto.VolunteerResponseDto;
import com.example.jamong.domain.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class VolunteerController {
    private final VolunteerService volunteerService;
    private final AwsS3Service awsS3Service;

    @GetMapping("/v1/volunteers")
    public List<VolunteerResponseDto> findAll() {
        return volunteerService.findAll();
    }

    @GetMapping("/v1/volunteers/{id}")
    public VolunteerResponseDto findById(@PathVariable Long id) {
        return volunteerService.findById(id);
    }

    @PostMapping("/v1/volunteers")
    public Volunteer save(@RequestBody VolunteerSaveRequestDto requestDto) {
        return volunteerService.save(requestDto);
    }

    @PostMapping("/upload")
    public String uploadFile(
            @RequestPart(value = "file") MultipartFile multipartFile) {
        return awsS3Service.uploadFileV1(multipartFile);
    }

    @PatchMapping("/v1/volunteers/{id}")
    public Volunteer update(@PathVariable Long id, @RequestBody VolunteerUpdateRequestDto requestDto) {
        return volunteerService.update(id, requestDto);
    }

    @DeleteMapping("/v1/volunteers/{id}")
    public Volunteer delete(@PathVariable Long id) {
        return volunteerService.delete(id);
    }
}
