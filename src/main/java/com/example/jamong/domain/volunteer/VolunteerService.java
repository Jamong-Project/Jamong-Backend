package com.example.jamong.domain.volunteer;

import com.example.jamong.domain.volunteer.dto.VolunteerResponseDto;
import com.example.jamong.domain.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.domain.volunteer.dto.VolunteerUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    @Transactional
    public List<VolunteerResponseDto> findAll() {
        List<Volunteer> volunteerList = volunteerRepository.findAll();

        List<VolunteerResponseDto> dtos = new ArrayList<>();

        for (Volunteer volunteer : volunteerList) {
            dtos.add(new VolunteerResponseDto(volunteer));
        }

        return dtos;
    }

    @Transactional
    public VolunteerResponseDto findById(Long id) {
        Volunteer entity = volunteerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다 id =" + id));
        return new VolunteerResponseDto(entity);
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
