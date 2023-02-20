package com.example.jamong.volunteer.facade;

import com.example.jamong.user.domain.User;
import com.example.jamong.volunteer.domain.Application;
import com.example.jamong.volunteer.domain.Comment;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.*;
import com.example.jamong.volunteer.service.ApplicationService;
import com.example.jamong.volunteer.service.CommentService;
import com.example.jamong.volunteer.service.FavoriteService;
import com.example.jamong.volunteer.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class VolunteerFacade {
    private final VolunteerService volunteerService;
    private final FavoriteService favoriteService;
    private final CommentService commentService;
    private final ApplicationService applicationService;

    @Transactional(readOnly = true)
    public VolunteerArticleResponseDto getVolunteerArticleById(Long id) {
        Volunteer volunteerEntity = volunteerService.getVolunteerById(id);

        return VolunteerArticleResponseDto.builder()
                .entity(volunteerEntity)
                .pictures(volunteerEntity.getDetailPicture())
                .applicants(getAppliedUsers(volunteerEntity))
                .favoriteUsers(getFavoriteUsers(volunteerEntity))
                .comments(getComments(volunteerEntity))
                .build();
    }

    private List<User> getAppliedUsers(Volunteer volunteer) {
        return applicationService.getApplicationsByVolunteer(volunteer)
                .stream()
                .map(Application::getUser)
                .collect(Collectors.toList());
    }

    private List<User> getFavoriteUsers(Volunteer volunteer) {
        return favoriteService.getFavoriteByVolunteer(volunteer)
                .stream()
                .map(Favorite::getUser)
                .collect(Collectors.toList());
    }

    private List<Comment> getComments(Volunteer volunteer) {
        return commentService.getCommentsByVolunteer(volunteer);
    }

    @Transactional(readOnly = true)
    public List<VolunteerCardResponseDto> getVolunteerCards(Pageable pageable) {
        return volunteerService.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public HttpHeaders getTotalPage() {
        return volunteerService.getTotalPage();
    }

    @Transactional
    public Volunteer save(VolunteerSaveRequestDto requestDto) {
        return volunteerService.save(requestDto);
    }

    @Transactional
    public Volunteer update(Long id, VolunteerUpdateRequestDto requestDto) {
        return volunteerService.update(id, requestDto);
    }

    @Transactional
    public void delete(Long id) {
        volunteerService.delete(id);
    }

}
