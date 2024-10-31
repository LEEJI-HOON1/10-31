package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter

public class ScheduleRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
