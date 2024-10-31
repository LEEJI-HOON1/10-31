package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long userId; // 작성자 ID


    // Schedule 엔티티를 기반으로 ScheduleResponseDto 생성
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.userId = schedule.getUser().getId();

    }
}
