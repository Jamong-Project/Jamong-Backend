package com.example.jamong.volunteer.service;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public List<Favorite> getFavoriteByVolunteer(Volunteer entity) {
        List<Favorite> favorites = favoriteRepository.findByVolunteer(entity);
        return favorites;
    }

    public void save(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public void deleteByUserAndVolunteer(User user, Volunteer volunteer) {
        favoriteRepository.deleteByUserAndVolunteer(user, volunteer);
    }

    public boolean isPressedFavorite(Long volunteerId, UserEmailRequestDto userEmailRequestDto) {
        return favoriteRepository.existsByVolunteerIdAndUserEmail(volunteerId, userEmailRequestDto.getEmail());
    }
}
