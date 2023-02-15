package com.example.jamong.volunteer.controller;

import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.*;
import com.example.jamong.volunteer.service.AwsS3Service;
import com.example.jamong.volunteer.service.VolunteerFacade;
import com.example.jamong.volunteer.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/volunteers")
public class VolunteerController {
    private final VolunteerFacade volunteerFacade;
    private final AwsS3Service awsS3Service;

    @GetMapping
    public ResponseEntity<List<VolunteerCardResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok()
                .headers(volunteerFacade.getTotalPage())
                .body(volunteerFacade.getVolunteerCards(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerArticleResponseDto> findById(@PathVariable Long id) {
        VolunteerArticleResponseDto volunteerArticle = volunteerFacade.getVolunteerArticleById(id);
        return ResponseEntity.ok().body(volunteerArticle);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Volunteer> save(@RequestPart(value = "request") VolunteerSaveRequestDto requestDto, @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) {

        if (multipartFile != null && !multipartFile.isEmpty()) {
            requestDto.setPictures(awsS3Service.uploadFile(multipartFile));
        }
        Volunteer saved = volunteerFacade.save(requestDto);
        return ResponseEntity.created(URI.create("/v1/volunteers/" + saved.getId())).body(saved);
    }

    @PostMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Volunteer> update(@PathVariable Long id, @RequestPart(value = "request") VolunteerUpdateRequestDto requestDto, @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            requestDto.setPictures(awsS3Service.uploadFile(multipartFile));
        }
        Volunteer updated = volunteerFacade.update(id, requestDto);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        volunteerFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> applyVolunteer(@PathVariable Long id, @RequestBody UserEmailRequestDto requestDto) {
        if (volunteerFacade.isAppliedVolunteer(id, requestDto)) {
            return ResponseEntity.created(URI.create("/v1/volunteers/" + id)).build();
        }

        volunteerFacade.applyVolunteer(id, requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/favorites")
    public ResponseEntity<Void> pressFavorite(@PathVariable Long id, @RequestBody UserEmailRequestDto requestDto) {
        if (volunteerFacade.isPressedFavorite(id, requestDto)) {
            volunteerFacade.pressFavorite(id, requestDto);
            return ResponseEntity.created(URI.create("/v1/volunteers/" + id)).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        volunteerFacade.addComment(id, commentRequestDto);
        return ResponseEntity.created(URI.create("/v1/volunteers/" + id)).build();
    }
}
