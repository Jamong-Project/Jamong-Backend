package com.example.jamong.volunteer.service;

import com.example.jamong.exception.FromBiggerThanToException;
import com.example.jamong.exception.NoExistUserException;
import com.example.jamong.exception.NoExistVolunteerException;
import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.user.repository.UserRepository;
import com.example.jamong.volunteer.domain.ApplyList;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.*;
import com.example.jamong.volunteer.repository.ApplyListRepository;
import com.example.jamong.volunteer.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class VolunteerService {

    private static final String DEFAULT_ORDERING_OPTION = "id";
    private static final int DESC_OPTION_CHARACTER_INDEX = 0;
    private static final char DESC_OPTION_CHARACTER = '-';
    private static final int DEFAULT_FROM_INDEX = 0;

    private final VolunteerRepository volunteerRepository;
    private final ApplyListRepository applyListRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<List<VolunteerCardDto>> findAll(Integer from, Integer to, String ordering) {
        Direction sort = Direction.ASC;

        ordering = orderingEmptyChecker(ordering);

        isFromBiggerThanTo(from, to);
        from = fromEmptyChecker(from);
        to = toEmptyChecker(from, to);

        if (sortOptionFinder(ordering)) {
            sort = Direction.DESC;
            ordering = ordering.substring(1);
        }

        List<Volunteer> volunteerList = volunteerRepository.findAll(Sort.by(sort, ordering));

        return getSubList(from, to, volunteerList);
    }

    private void isFromBiggerThanTo(Integer from, Integer to) {
        if (from != null && to != null && from > to) {
            throw new FromBiggerThanToException();
        }
    }

    private String orderingEmptyChecker(String ordering) {
        if (ordering == null) {
            ordering = DEFAULT_ORDERING_OPTION;
        }
        return ordering;
    }

    private Integer toEmptyChecker(Integer from, Integer to) {
        if (to == null) {
            to = from + 11;
        }
        return to;
    }

    private Integer fromEmptyChecker(Integer from) {
        if (from == null) {
            from = DEFAULT_FROM_INDEX;
        }
        return from;
    }

    private boolean sortOptionFinder(String ordering) {
        return ordering.charAt(DESC_OPTION_CHARACTER_INDEX) == DESC_OPTION_CHARACTER;
    }

    private ResponseEntity<List<VolunteerCardDto>> getSubList(Integer from, Integer to, List<Volunteer> volunteerList) {
        final int totalPage = volunteerList.size();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("total-page", String.valueOf(totalPage));

        if (to > totalPage) {
            return getResponseEntity(volunteerList, from, totalPage, responseHeaders);
        }

        return getResponseEntity(volunteerList, from, to + 1, responseHeaders);
    }

    private ResponseEntity<List<VolunteerCardDto>> getResponseEntity(List<Volunteer> volunteerList, Integer from, Integer to, HttpHeaders responseHeaders) {
        List<VolunteerCardDto> dtos = new ArrayList<>();
        for (Volunteer volunteer : volunteerList.subList(from, to)) {
            dtos.add(new VolunteerCardDto(volunteer));
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(dtos);
    }

    @Transactional
    public VolunteerArticleDto findById(Long id) {
        Volunteer entity = volunteerRepository.findById(id)
                .orElseThrow(
                        () -> new NoExistVolunteerException()
                );
        List<ApplyList> applyLists = applyListRepository.findByVolunteer(entity);

        List<User> applicants = applyLists.stream()
                .map(ApplyList::getUser)
                .collect(Collectors.toList());

        return VolunteerArticleDto.builder()
                .entity(entity)
                .applicants(applicants)
                .build();
    }

    @Transactional
    public Volunteer save(VolunteerSaveRequestDto requestDto) {
        return volunteerRepository.save(requestDto.toEntity());
    }

    @Transactional
    public Volunteer update(Long id, VolunteerUpdateRequestDto requestDto) {
        Volunteer entity = volunteerRepository.findById(id)
                .orElseThrow(
                        () -> new NoExistVolunteerException()
                );
        entity.update(requestDto);

        return volunteerRepository.save(entity);

    }

    @Transactional
    public Volunteer delete(Long id) {
        Volunteer entity = volunteerRepository.findById(id)
                .orElseThrow(
                        () -> new NoExistVolunteerException()
                );

        volunteerRepository.delete(entity);
        return entity;
    }

    @Transactional
    public void addUser(Long volunteerId, UserEmailRequestDto userEmailRequestDto) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(
                        () -> new NoExistVolunteerException()
                );
        List<User> user = userRepository.findByEmail(userEmailRequestDto.getEmail());

        if (user.isEmpty()) {
            throw new NoExistVolunteerException();
        }

        ApplyList applyList = ApplyList.builder()
                .volunteer(volunteer)
                .user(user.get(0))
                .build();

        applyListRepository.save(applyList);

    }
}