package com.example.jamong.volunteer.service;

import com.example.jamong.exception.NoExistVolunteerException;
import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.user.repository.UserRepository;
import com.example.jamong.volunteer.domain.ApplyList;
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

import javax.transaction.Transactional;
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

    @Transactional
    public ResponseEntity<List<VolunteerCardResponseDto>> findAll(Pageable pageable) {
        List<Volunteer> volunteerList = volunteerRepository.findAll(pageable).getContent();
        List<VolunteerCardResponseDto> volunteerCardResponseDtoList = volunteerList.stream()
                .map(Volunteer::toCardDto)
                .collect(Collectors.toList());

        final int totalPage = volunteerList.size();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("total-page", String.valueOf(totalPage));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(volunteerCardResponseDtoList);
    }

    @Transactional
    public VolunteerArticleResponseDto findById(Long id) {
        Volunteer entity = volunteerRepository.findById(id).orElseThrow(NoExistVolunteerException::new);

        List<ApplyList> applyLists = applyListRepository.findByVolunteer(entity);
        List<Favorite> favorites = favoriteRepository.findByVolunteer(entity);
        List<Comment> commentList = commentRepository.findByVolunteer(entity);

        List<User> applicants = applyLists.stream()
                .map(ApplyList::toDto)
                .map(ApplyListResponseDto::getUser)
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
    public Volunteer delete(Long id) {
        Volunteer entity = volunteerRepository.findById(id).orElseThrow(NoExistVolunteerException::new);

        volunteerRepository.delete(entity);
        return entity;
    }

    @Transactional
    public boolean applyVolunteer(Long volunteerId, UserEmailRequestDto userEmailRequestDto) {
        List<User> user = userRepository.findByEmail(userEmailRequestDto.getEmail());

        if (user.isEmpty()) {
            throw new NoExistVolunteerException();
        }

        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(NoExistVolunteerException::new);

        List<String> userList = volunteer.getApplyLists().stream()
                .map(ApplyList::toDto)
                .map(ApplyListResponseDto::getUser)
                .map(User::toString)
                .collect(Collectors.toList());

        if (!userList.contains(user.get(0).toString())) {

            ApplyList applyList = ApplyList.builder()
                    .volunteer(volunteer)
                    .user(user.get(0))
                    .build();

            applyListRepository.save(applyList);
            updateCurrentUser(volunteer);
            return true;
        }

        volunteer.getApplyLists().remove(applyListRepository.findByUserAndVolunteer(user.get(0), volunteer).get(0));
        applyListRepository.deleteByUserAndVolunteer(user.get(0), volunteer);
        updateCurrentUser(volunteer);
        return false;
    }

    private void updateCurrentUser(Volunteer volunteer) {
        List<ApplyList> userList = applyListRepository.findByVolunteer(volunteer);
        int size = userList.size();

        volunteer.setCurrentPeople(size);
        volunteerRepository.save(volunteer);
    }

    @Transactional
    public boolean pressFavorite(Long volunteerId, UserEmailRequestDto userEmailRequestDto) {
        List<User> user = userRepository.findByEmail(userEmailRequestDto.getEmail());

        if (user.isEmpty()) {
            throw new NoExistVolunteerException();
        }

        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(NoExistVolunteerException::new);

        List<String> userList = volunteer.getFavorites().stream()
                .map(Favorite::toDto)
                .map(FavoriteResponseDto::getUser)
                .map(User::toString)
                .collect(Collectors.toList());

        if (!userList.contains(user.get(0).toString())) {

            Favorite favorite = Favorite.builder()
                    .volunteer(volunteer)
                    .user(user.get(0))
                    .build();

            favoriteRepository.save(favorite);
            updateCurrentUser(volunteer);
            return true;
        }

        volunteer.getFavorites().remove(favoriteRepository.findByUserAndVolunteer(user.get(0), volunteer).get(0));
        favoriteRepository.deleteByUserAndVolunteer(user.get(0), volunteer);
        updateCurrentUser(volunteer);
        return false;
    }

    @Transactional
    public Volunteer addComment(Long volunteerId, CommentRequestDto requestDto) {
        List<User> users = userRepository.findByEmail(requestDto.getEmail());

        if (users.isEmpty()) {
            throw new NoExistVolunteerException();
        }

        User user = users.get(0);
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow(NoExistVolunteerException::new);

        Comment comment = Comment.builder()
                .user(user)
                .volunteer(volunteer)
                .content(requestDto.getContent())
                .build();

        volunteer.addComment(comment);

        return volunteerRepository.save(volunteer);
    }
}