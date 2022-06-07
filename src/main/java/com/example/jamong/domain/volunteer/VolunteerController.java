package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.s3.AwsS3Service;
import com.example.jamong.domain.volunteer.dto.VolunteerResponseDto;
import com.example.jamong.domain.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VolunteerController {
    private final VolunteerService volunteerService;
    private final AwsS3Service awsS3Service;

    @GetMapping("/v1/volunteers")
    public ResponseEntity<List<VolunteerResponseDto>> findAll(@RequestParam(required = false) Integer to, @RequestParam(required = false) Integer from,
                                                              @RequestParam(required = false) String ordering) {

        return volunteerService.findAll(to, from, ordering);
    }

    @GetMapping("/v1/volunteers/{id}")
    public VolunteerResponseDto findById(@PathVariable Long id) {
        return volunteerService.findById(id);
    }

    @PostMapping(value = "/v1/volunteers", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Volunteer save(@RequestPart(value = "request") VolunteerSaveRequestDto requestDto, @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            log.info(requestDto.setPicture(awsS3Service.uploadFileV1(multipartFile)));
        }
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
