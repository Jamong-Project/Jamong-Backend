package com.example.jamong.volunteer.service;

import com.example.jamong.exception.NoExistUserException;
import com.example.jamong.exception.NoExistVolunteerException;
import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.user.repository.UserRepository;
import com.example.jamong.volunteer.domain.Apply;
import com.example.jamong.volunteer.domain.Comment;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.*;
import com.example.jamong.volunteer.repository.ApplyListRepository;
import com.example.jamong.volunteer.repository.CommentRepository;
import com.example.jamong.volunteer.repository.FavoriteRepository;
import com.example.jamong.volunteer.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final ApplyListRepository applyListRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<VolunteerCardResponseDto>> findAll(Pageable pageable) {
        List<Volunteer> volunteerList = volunteerRepository.findAll(pageable).getContent();
        List<Volunteer> allVolunteerList = volunteerRepository.findAll();
        List<VolunteerCardResponseDto> volunteerCardResponseDtoList = volunteerList.stream()
                .map(Volunteer::toCardDto)
                .collect(Collectors.toList());

        final int totalPage = allVolunteerList.size();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("total-page", String.valueOf(totalPage));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(volunteerCardResponseDtoList);
    }

    @Transactional(readOnly = true)
    public VolunteerArticleResponseDto findById(Long id) {
        Volunteer entity = volunteerRepository.findById(id).orElseThrow(NoExistVolunteerException::new);

        List<Apply> applies = applyListRepository.findByVolunteer(entity);
        List<Favorite> favorites = favoriteRepository.findByVolunteer(entity);
        List<Comment> commentList = commentRepository.findByVolunteer(entity);

        List<User> applicants = applies.stream()
                .map(Apply::toDto)
                .map(ApplyResponseDto::getUser)
                .collect(Collectors.toList());

        List<User> favoriteUsers = favorites.stream()
                .map(Favorite::toDto)
                .map(FavoriteResponseDto::getUser)
                .collect(Collectors.toList());

        List<CommentResponseDto> commentListDto = commentList.stream()
                .map(Comment::toDto)
                .collect(Collectors.toList());

        return VolunteerArticleResponseDto.builder()
                .entity(entity)
                .pictures(entity.detailPicture())
                .applicants(applicants)
                .favoriteUsers(favoriteUsers)
                .comments(commentListDto)
                .build();
    }

    @Transactional
    public Volunteer save(VolunteerSaveRequestDto requestDto) {
        return volunteerRepository.save(requestDto.toEntity());
    }

    @Transactional
    public Volunteer update(Long id, VolunteerUpdateRequestDto requestDto) {
        Volunteer entity = volunteerRepository.findById(id).orElseThrow(NoExistVolunteerException::new);
        entity.update(requestDto);

        return volunteerRepository.save(entity);

    }

    @Transactional
    public void delete(Long id) {
        Volunteer entity = volunteerRepository.findById(id).orElseThrow(NoExistVolunteerException::new);

        volunteerRepository.delete(entity);
    }

    private void updateCurrentUser(Volunteer volunteer) {
        List<Apply> userList = applyListRepository.findByVolunteer(volunteer);
        int size = userList.size();

        volunteer.setCurrentPeople(size);
        volunteerRepository.save(volunteer);
    }

    @Transactional
    public boolean isApplyVolunteer(Long volunteerId, UserEmailRequestDto userEmailRequestDto) {
        User user = userRepository.findByEmail(userEmailRequestDto.getEmail()).orElseThrow(NoExistUserException::new);

        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(NoExistVolunteerException::new);

        List<User> userList = volunteer.getApplies().stream()
                .map(Apply::toDto)
                .map(ApplyResponseDto::getUser)
                .collect(Collectors.toList());

        if (!userList.contains(user)) {

            Apply applyList = Apply.builder()
                    .volunteer(volunteer)
                    .user(user)
                    .build();

            applyListRepository.save(applyList);
            updateCurrentUser(volunteer);
            return true;
        }
        volunteer.getApplies().remove(applyListRepository.findByUserAndVolunteer(user, volunteer).get(0));
        applyListRepository.deleteByUserAndVolunteer(user, volunteer);
        updateCurrentUser(volunteer);
        return false;
    }


    @Transactional
    public boolean isPressFavorite(Long volunteerId, UserEmailRequestDto userEmailRequestDto) {
        User user = userRepository.findByEmail(userEmailRequestDto.getEmail()).orElseThrow(NoExistUserException::new);
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(NoExistVolunteerException::new);

        List<User> userList = volunteer.getFavorites().stream()
                .map(Favorite::toDto)
                .map(FavoriteResponseDto::getUser)
                .collect(Collectors.toList());

        if (!userList.contains(user)) {

            Favorite favorite = Favorite.builder()
                    .volunteer(volunteer)
                    .user(user)
                    .build();

            favoriteRepository.save(favorite);
            updateCurrentUser(volunteer);
            return true;
        }

        volunteer.getFavorites().remove(favoriteRepository.findByUserAndVolunteer(user, volunteer).get(0));
        favoriteRepository.deleteByUserAndVolunteer(user, volunteer);
        updateCurrentUser(volunteer);
        return false;
    }

    @Transactional
    public void addComment(Long volunteerId, CommentRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(NoExistUserException::new);
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(NoExistVolunteerException::new);

        Comment comment = Comment.builder()
                .user(user)
                .volunteer(volunteer)
                .content(requestDto.getContent())
                .build();

        volunteer.addComment(comment);
        volunteerRepository.save(volunteer);
    }
}