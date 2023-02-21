package com.example.jamong.volunteer.mapper;

import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.VolunteerCardResponseDto;
import com.example.jamong.volunteer.dto.VolunteerResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VolunteerMapper {
    VolunteerResponseDto toResponseDto(Volunteer entity);
    VolunteerCardResponseDto toCardResponseDto(Volunteer entity);
}
