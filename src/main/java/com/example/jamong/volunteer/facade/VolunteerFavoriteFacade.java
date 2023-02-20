package com.example.jamong.volunteer.facade;

import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserEmailRequestDto;
import com.example.jamong.user.service.UserService;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.FavoriteResponseDto;
import com.example.jamong.volunteer.service.FavoriteService;
import com.example.jamong.volunteer.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class VolunteerFavoriteFacade {

    private final UserService userService;
    private final VolunteerService volunteerService;
    private final FavoriteService favoriteService;

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
}
