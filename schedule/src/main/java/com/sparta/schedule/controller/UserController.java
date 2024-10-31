package com.sparta.schedule.controller;

import com.sparta.schedule.auth.TokenInfo;
import com.sparta.schedule.dto.UserRequestDto;
import com.sparta.schedule.dto.UserResponseDto;
import com.sparta.schedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 유저 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    // 유저 정보 업데이트
    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = userService.updateUser(userRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 유저 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.noContent().build();
    }
}
