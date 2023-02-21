package com.example.jamong.volunteer.controller;

import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.volunteer.dto.*;
import com.example.jamong.volunteer.facade.VolunteerApplicationFacade;
import com.example.jamong.volunteer.facade.VolunteerCommentFacade;
import com.example.jamong.volunteer.facade.VolunteerFavoriteFacade;
import com.example.jamong.volunteer.service.AwsS3Service;
import com.example.jamong.volunteer.facade.VolunteerFacade;
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
    private final AwsS3Service awsS3Service;
    private final VolunteerFacade volunteerFacade;
    private final VolunteerApplicationFacade volunteerApplicationFacade;
    private final VolunteerFavoriteFacade volunteerFavoriteFacade;
    private final VolunteerCommentFacade volunteerCommentFacade;

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
    public ResponseEntity<VolunteerResponseDto> save(@RequestPart(value = "request") VolunteerSaveRequestDto requestDto, @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) {

        if (multipartFile != null && !multipartFile.isEmpty()) {
            requestDto.setPictures(awsS3Service.uploadFile(multipartFile));
        }
        VolunteerResponseDto saved = volunteerFacade.save(requestDto);
        return ResponseEntity.created(URI.create("/v1/volunteers/" + saved.getId())).body(saved);
    }

    @PostMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<VolunteerResponseDto> update(@PathVariable Long id, @RequestPart(value = "request") VolunteerUpdateRequestDto requestDto, @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            requestDto.setPictures(awsS3Service.uploadFile(multipartFile));
        }
        VolunteerResponseDto updated = volunteerFacade.update(id, requestDto);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        volunteerFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> applyVolunteer(@PathVariable Long id, @RequestBody UserEmailRequestDto requestDto) {
        if (volunteerApplicationFacade.isAppliedVolunteer(id, requestDto)) {
            return ResponseEntity.created(URI.create("/v1/volunteers/" + id)).build();
        }

        volunteerApplicationFacade.applyVolunteer(id, requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/favorites")
    public ResponseEntity<Void> pressFavorite(@PathVariable Long id, @RequestBody UserEmailRequestDto requestDto) {
        if (volunteerFavoriteFacade.isPressedFavorite(id, requestDto)) {
            volunteerFavoriteFacade.pressFavorite(id, requestDto);
            return ResponseEntity.created(URI.create("/v1/volunteers/" + id)).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        volunteerCommentFacade.addComment(id, commentRequestDto);
        return ResponseEntity.created(URI.create("/v1/volunteers/" + id)).build();
    }
}
