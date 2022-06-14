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

    private static final String DEFAULT_ORDERING_OPTION = "id";
    private static final int DESC_OPTION_CHARACTER_INDEX = 0;
    private static final char DESC_OPTION_CHARACTER = '-';
    private static final int DEFAULT_FROM_INDEX = 0;

    private final VolunteerRepository volunteerRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public ResponseEntity<List<VolunteerCardDto>> findAll(Integer to, Integer from, String ordering) {
        Direction sort = Direction.ASC;

        ordering = orderingEmptyChecker(ordering);
        from = fromEmptyChecker(from);
        to = toEmptyChecker(to, from);

        if (sortOptionFinder(ordering)) {
            sort = Direction.DESC;
            ordering = ordering.substring(1);
        }

        List<Volunteer> volunteerList = volunteerRepository.findAll(Sort.by(sort, ordering));

        return getSubList(to, from, volunteerList);
    }

    private Integer toEmptyChecker(Integer to, Integer from) {
        if (to == null) {
            to = from + 12;
        }
        return to;
    }

    private String orderingEmptyChecker(String ordering) {
        if (ordering == null) {
            ordering = DEFAULT_ORDERING_OPTION;
        }
        return ordering;
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

    private ResponseEntity<List<VolunteerCardDto>> getSubList(Integer to, Integer from, List<Volunteer> volunteerList) {
        final int totalPage = volunteerList.size();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("total-page", String.valueOf(totalPage));

        if (to > totalPage) {
            return getResponseEntity(volunteerList, from, totalPage,responseHeaders);
        }

        return getResponseEntity(volunteerList, from, to, responseHeaders);
    }

    private ResponseEntity<List<VolunteerCardDto>> getResponseEntity(List<Volunteer> volunteerList, Integer from, int totalPage, HttpHeaders responseHeaders) {
        List<VolunteerCardDto> dtos = new ArrayList<>();
        for (Volunteer volunteer : volunteerList.subList(from, totalPage)) {
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
