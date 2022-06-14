package com.example.jamong.volunteer.controller;

import com.example.jamong.volunteer.service.AwsS3Service;
import com.example.jamong.volunteer.service.VolunteerService;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.VolunteerArticleDto;
import com.example.jamong.volunteer.dto.VolunteerCardDto;
import com.example.jamong.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<VolunteerCardDto>> findAll(@RequestParam(required = false) Integer to, @RequestParam(required = false) Integer from,
                                                          @RequestParam(required = false) String ordering) {

        return volunteerService.findAll(to, from, ordering);
    }

    @GetMapping("/v1/volunteers/{id}")
    public VolunteerArticleDto findById(@PathVariable Long id) {
        return volunteerService.findById(id);
    }

    @PostMapping(value = "/v1/volunteers", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Volunteer save(@RequestPart(value = "request") VolunteerSaveRequestDto requestDto, @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) {

        if (multipartFile != null && !multipartFile.isEmpty()) {
            requestDto.setPictures(awsS3Service.uploadFile(multipartFile));
        }
        return volunteerService.save(requestDto);
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
