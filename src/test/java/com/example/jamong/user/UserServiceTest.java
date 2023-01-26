package com.example.jamong.user;

import com.example.jamong.exception.NoExistUserException;
import com.example.jamong.user.domain.Role;
import com.example.jamong.user.domain.User;
import com.example.jamong.user.dto.UserUpdateRequestDto;
import com.example.jamong.user.repository.UserRepository;
import com.example.jamong.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        String naverId = "1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM";
        String profileImage = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";
        String email = "lmj938@naver.com";
        String name = "이민재";
        Role role = Role.GUEST;

        userRepository.save(
                User.builder()
                        .naverId(naverId)
                        .profileImage(profileImage)
                        .email(email)
                        .name(name)
                        .role(role)
                        .build()
        );
    }

    @AfterEach
    public void CleanUp() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("유저 정보를 모두 조회한다.")
    public void findAllTest() {
        List<User> userList = userService.findAll(null, null);
        assertThat(userList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저를 이메일로 조회한다.")
    public void findByEmailTest() {
        User actualUser = userService.findAll("lmj938@naver.com", null).get(0);

        assertThat(actualUser.getNaverId()).isEqualTo("1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM");
        assertThat(actualUser.getProfileImage()).isEqualTo("https://ssl.pstatic.net/static/pwe/address/img_profile.png");
        assertThat(actualUser.getEmail()).isEqualTo("lmj938@naver.com");
        assertThat(actualUser.getName()).isEqualTo("이민재");
        assertThat(actualUser.getRole()).isEqualTo(Role.GUEST);
    }

    @Test
    @DisplayName("유저를 이름으로 조회한다.")
    public void findByNameTest() {
        List<User> actualUser = userService.findAll(null, "이민재");

        assertThat(actualUser.get(0).getNaverId()).isEqualTo("1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM");
        assertThat(actualUser.get(0).getProfileImage()).isEqualTo("https://ssl.pstatic.net/static/pwe/address/img_profile.png");
        assertThat(actualUser.get(0).getEmail()).isEqualTo("lmj938@naver.com");
    }

    @Test
    @DisplayName("유저의 정보를 변경한다.")
    public void updateTest() {
        String updatedProfileImage = "https://ssl.pstatic.net/static/pwe/address/img_profile.pngupdated";
        String updatedEmail = "lmj938@naver.comupdated";
        Role updatedRole = Role.USER;
        User user = userService.findAll(null, null).get(0);

        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .email(updatedEmail)
                .profileImage(updatedProfileImage)
                .role(updatedRole)
                .build();

        userService.update(user.getId(), userUpdateRequestDto);

        assertThat(userService.findAll(updatedEmail, null).get(0).getProfileImage()).isEqualTo("https://ssl.pstatic.net/static/pwe/address/img_profile.pngupdated");
        assertThat(userService.findAll(updatedEmail, null).get(0).getEmail()).isEqualTo("lmj938@naver.comupdated");
        assertThat(userService.findAll(updatedEmail, null).get(0).getRole()).isEqualTo(Role.USER);
    }

    @Test
    @DisplayName("유저를 삭제한다.")
    public void deleteTest() {
        User user = userService.findAll(null, null).get(0);
        userService.delete(user.getId());

        assertThat(userService.findAll(null, null).size()).isEqualTo(0);

        String naverId = "1lOmnoQs0-GTI3XEOxmUOn1Fjm91IjLpyb4K7_kxzSM";
        String profileImage = "https://ssl.pstatic.net/static/pwe/address/img_profile.png";
        String email = "lmj938@naver.com";
        String name = "이민재";
        Role role = Role.GUEST;

        userRepository.save(
                User.builder()
                        .naverId(naverId )
                        .profileImage(profileImage)
                        .email(email)
                        .name(name)
                        .role(role)
                        .build()
        );
    }

    @Test
    @DisplayName("없는 유저를 찾을 시 NoExistUserException이 발생한다.")
    public void noExistExceptionTest() {
        assertThatThrownBy(() -> userService.findById(52L)).isInstanceOf(NoExistUserException.class);
        assertThatThrownBy(() -> userService.delete(52L)).isInstanceOf(NoExistUserException.class);
    }
}
