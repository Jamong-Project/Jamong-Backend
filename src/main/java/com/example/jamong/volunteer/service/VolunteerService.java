package com.example.jamong.volunteer.service;

import com.example.jamong.volunteer.repository.VolunteerRepository;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.VolunteerArticleDto;
import com.example.jamong.volunteer.dto.VolunteerCardDto;
import com.example.jamong.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VolunteerService {

    private final static String DEFAULT_ORDERING_OPTION = "id";
    private final static Integer DESC_OPTION_CHARACTER_INDEX = 0;
    private final static char DESC_OPTION_CHARACTER = '-';
    private final static Integer DEFAULT_FROM_INDEX = 0;
    private final static Integer DEFAULT_TO_INDEX = 11;

    private final VolunteerRepository volunteerRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public ResponseEntity<List<VolunteerCardDto>> findAll(Integer to, Integer from, String ordering) {
        List<Volunteer> volunteerList;
        Direction sort = Direction.ASC;
        HttpHeaders responseHeaders = new HttpHeaders();

        if (ordering == null) {
            ordering = DEFAULT_ORDERING_OPTION;
        }

        if (ordering.charAt(DESC_OPTION_CHARACTER_INDEX) == DESC_OPTION_CHARACTER) {
            sort = Direction.DESC;
            ordering = ordering.substring(1);
        }

        volunteerList = volunteerRepository.findAll(Sort.by(sort, ordering));

        int totalPage = volunteerList.size();
        responseHeaders.set("total-page", String.valueOf(totalPage));
        List<VolunteerCardDto> dtos = new ArrayList<>();


        if (from == null) {
            from = DEFAULT_FROM_INDEX;

            if (to != null) {
                to = totalPage;
            }
        }

        if (to == null) {
            to = DEFAULT_TO_INDEX;
        }

        if (to > totalPage) {
            for (Volunteer volunteer : volunteerList.subList(from, totalPage)) {
                dtos.add(new VolunteerCardDto(volunteer));
            }
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(dtos);
        }

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
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 id =" + id));
        return new VolunteerArticleDto(entity);
    }

    @Transactional
    public Volunteer save(VolunteerSaveRequestDto requestDto) {
        return volunteerRepository.save(requestDto.toEntity());
    }

    @Transactional
    public Volunteer update(Long id, VolunteerUpdateRequestDto requestDto) {
        Volunteer entity = volunteerRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 게시글이 없습니다 id =" + id)
                );

        entity.update(requestDto);

        return volunteerRepository.save(entity);

    }

    @Transactional
    public Volunteer delete(Long id) {
        Volunteer entity = volunteerRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 게시글이 없습니다 id =" + id)
                );

        volunteerRepository.delete(entity);
        return entity;
    }

}
