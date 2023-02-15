package com.example.jamong.volunteer.service;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.user.service.UserService;
import com.example.jamong.volunteer.domain.Application;
import com.example.jamong.volunteer.domain.Comment;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.*;
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
    private final UserService userService;

    @Transactional(readOnly = true)
    public VolunteerArticleResponseDto getVolunteerArticleById(Long id) {
        Volunteer volunteerEntity = volunteerService.getVolunteerById(id);

        List<Application> applyList = applicationService.getApplicationsByVolunteer(volunteerEntity);
        List<Favorite> favoriteList = favoriteService.getFavoriteByVolunteer(volunteerEntity);
        List<Comment> commentList = commentService.getCommentsByVolunteer(volunteerEntity);

        List<User> applyUserList = applyList.stream()
                .map(Application::getUser)
                .collect(Collectors.toList());

        List<User> favoriteUserList = favoriteList.stream()
                .map(Favorite::getUser)
                .collect(Collectors.toList());

        return VolunteerArticleResponseDto.builder()
                .entity(volunteerEntity)
                .pictures(volunteerEntity.detailPicture())
                .applicants(applyUserList)
                .favoriteUsers(favoriteUserList)
                .comments(commentList)
                .build();
    }

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

    @Transactional(readOnly = true)
    public List<VolunteerCardResponseDto> getVolunteerCards(Pageable pageable) {
        return volunteerService.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public HttpHeaders getTotalPage() {
        return volunteerService.getTotalPage();
    }

    @Transactional
    public void pressFavorite(Long volunteerId, UserEmailRequestDto userEmailRequestDto) {
        User user = userService.getUserByEmail(userEmailRequestDto.getEmail());
        Volunteer volunteer = volunteerService.getVolunteerById(volunteerId);

        List<User> userList = volunteer.getFavorites().stream()
                .map(Favorite::toDto)
                .map(FavoriteResponseDto::getUser)
                .collect(Collectors.toList());

        Favorite favorite = Favorite.builder()
                .volunteer(volunteer)
                .user(user)
                .build();

        if (!userList.contains(user)) {
            volunteer.addFavorite(favorite);
            favoriteService.save(favorite);
            return;
        }

        volunteer.removeFavorite(favorite);
        favoriteService.deleteByUserAndVolunteer(user, volunteer);
        return;
    }

    @Transactional(readOnly = true)
    public boolean isPressedFavorite(Long volunteerId, UserEmailRequestDto userEmailRequestDto) {
        return favoriteService.isPressedFavorite(volunteerId, userEmailRequestDto);
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

    @Transactional
    public Comment addComment(Long volunteerId, CommentRequestDto requestDto) {
        User user = userService.getUserByEmail(requestDto.getEmail());
        Volunteer volunteer = volunteerService.getVolunteerById(volunteerId);

        Comment comment = Comment.builder()
                .user(user)
                .volunteer(volunteer)
                .content(requestDto.getContent())
                .build();

        volunteer.addComment(comment);
        return commentService.save(comment);
    }
}
