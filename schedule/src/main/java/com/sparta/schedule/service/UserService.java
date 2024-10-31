package com.sparta.schedule.service;

import com.sparta.schedule.auth.JwtTokenProvider;
import com.sparta.schedule.dto.UserRequestDto;
import com.sparta.schedule.dto.UserResponseDto;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.UserRepository;
import com.sparta.schedule.util.PasswordEncryptor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    // 회원가입
    public UserResponseDto createUser(UserRequestDto requestDto) {
        String encryptedPassword = passwordEncryptor.encryptPassword(requestDto.getPassword());
        User user = new User(requestDto,encryptedPassword);
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    // 유저 조회
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }

    // 유저 업데이트 - 토큰을 통해 로그인한 유저만 수정 가능
    @Transactional
    public UserResponseDto updateUser(UserRequestDto requestDto) {
        User loginedUser = jwtTokenProvider.getLoginedUser();
        loginedUser.update(requestDto);
        return new UserResponseDto(loginedUser);
    }

    // 유저 삭제 - 토큰을 통해 로그인한 유저만 삭제 가능
    @Transactional
    public void deleteUser() {
        User loginedUser = jwtTokenProvider.getLoginedUser();
        userRepository.delete(loginedUser);
    }
}
