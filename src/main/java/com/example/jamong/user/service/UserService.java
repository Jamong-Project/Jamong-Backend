package com.example.jamong.user.service;

import com.example.jamong.exception.NaverLoginFailException;
import com.example.jamong.exception.NoExistUserException;
import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.*;
import com.example.jamong.user.repository.UserRepository;
import com.example.jamong.volunteer.domain.Apply;
import com.example.jamong.volunteer.domain.Favorite;
import com.example.jamong.volunteer.domain.Volunteer;
import com.example.jamong.volunteer.dto.ApplyResponseDto;
import com.example.jamong.volunteer.dto.FavoriteResponseDto;
import com.example.jamong.volunteer.repository.ApplyListRepository;
import com.example.jamong.volunteer.repository.FavoriteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final static String NAVER_LOGIN_URL = "https://openapi.naver.com/v1/nid/me";
    private final static String NAVER_LOGIN_HEADER_STRING = "Bearer ";

    private final UserRepository userRepository;
    private final ApplyListRepository applyListRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<User> getProfile(TokenRequestDto tokenRequestDto) {
        UserSaveRequestDto userSaveRequestDto = getUserProfileFromNaver(tokenRequestDto);
        Optional<User> user = userRepository.findByEmail(userSaveRequestDto.getEmail());

        if (!user.isPresent()) {
            User saved = userRepository.save(userSaveRequestDto.toEntity());
            return ResponseEntity.created(URI.create("/v1/users/" + saved.getId())).body(saved);
        }

        return ResponseEntity.ok(user.get());
    }

    protected UserSaveRequestDto getUserProfileFromNaver(TokenRequestDto tokenRequestDto) {
        String jsonUserProfile = getJsonUserProfile(tokenRequestDto);
        NaverResponseDto naverResponseDto = jsonProfileParser(jsonUserProfile);

        if (naverResponseDto.getResultCode().equals("00")) {
            return naverResponseDto.getUserSaveRequestDto();
        }

        throw new NaverLoginFailException();
    }

    protected String getJsonUserProfile(TokenRequestDto tokenRequestDto) {
        String token = tokenRequestDto.getToken();
        String header = NAVER_LOGIN_HEADER_STRING + token;
        Map<String, String> requestHeaders = new HashMap<>();

        requestHeaders.put("Authorization", header);
        String responseBody = get(NAVER_LOGIN_URL, requestHeaders);
        return responseBody;
    }

    private NaverResponseDto jsonProfileParser(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        NaverResponseDto naverResponseDto = new NaverResponseDto();

        try {
            naverResponseDto = objectMapper.readValue(responseBody, NaverResponseDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return naverResponseDto;
    }

    @Transactional(readOnly = true)
    public String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            }
            return readBody(con.getErrorStream());

        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {

            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);

        } catch (IOException e) {

            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);

        }
    }

    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAll(String email, String name) {
        if (email != null && name == null) {
            return userRepository.findAllByEmail(email);
        }

        if (email == null && name != null) {
            return userRepository.findByName(name);
        }
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(NoExistUserException::new);

        List<Apply> applies = applyListRepository.findByUser(user);
        List<Favorite> favorites = favoriteRepository.findByUser(user);

        List<Volunteer> apply = applies.stream()
                .map(Apply::toDto)
                .map(ApplyResponseDto::getVolunteer)
                .collect(Collectors.toList());

        List<Volunteer> favoriteVolunteers = favorites.stream()
                .map(Favorite::toDto)
                .map(FavoriteResponseDto::getVolunteer)
                .collect(Collectors.toList());

        UserResponseDto dto = UserResponseDto.builder()
                .entity(user)
                .favoriteVolunteers(favoriteVolunteers)
                .volunteers(apply)
                .build();

        return dto;
    }

    @Transactional
    public User update(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findById(id).orElseThrow(NoExistUserException::new);

        user.update(userUpdateRequestDto);
        return userRepository.save(user);

    }

    @Transactional
    public User delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(NoExistUserException::new);
        userRepository.delete(user);
        return user;
    }
}