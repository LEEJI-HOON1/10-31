package com.sparta.schedule.dto;

import com.sparta.schedule.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;

    // User 엔티티를 기반으로 UserResponseDto 생성
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
