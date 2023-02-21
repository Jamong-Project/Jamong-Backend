package com.example.jamong.volunteer.service;

import com.example.jamong.exception.NoExistVolunteerException;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.VolunteerSaveRequestDto;
import com.example.jamong.volunteer.dto.VolunteerUpdateRequestDto;
import com.example.jamong.volunteer.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public List<Volunteer> findAll(Pageable pageable) {
        return volunteerRepository.findAll(pageable).getContent();
    }

    public HttpHeaders getTotalPage() {
        List<Volunteer> allVolunteerList = volunteerRepository.findAll();
        final int totalPage = allVolunteerList.size();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("total-page", String.valueOf(totalPage));
        
        return responseHeaders;
    }

    public Volunteer getVolunteerById (Long id) {
        return volunteerRepository.findById(id).orElseThrow(NoExistVolunteerException::new);
    }

    public Volunteer save(VolunteerSaveRequestDto requestDto) {
        return volunteerRepository.save(requestDto.toEntity());
    }

    public Volunteer update(Long id, VolunteerUpdateRequestDto requestDto) {
        Volunteer entity = volunteerRepository.findById(id).orElseThrow(NoExistVolunteerException::new);
        entity.update(requestDto);

        return volunteerRepository.save(entity);
    }

    public void delete(Long id) {
        Volunteer entity = volunteerRepository.findById(id).orElseThrow(NoExistVolunteerException::new);
        volunteerRepository.delete(entity);
    }
}