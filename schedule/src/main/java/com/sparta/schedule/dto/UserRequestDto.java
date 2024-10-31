package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
