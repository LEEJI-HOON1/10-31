package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 스케줄 생성
    @PostMapping("/create")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 스케줄 전체 조회 (페이징)
    @GetMapping("/all")
    public ResponseEntity<Page<ScheduleResponseDto>> getPagedSchedules(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<ScheduleResponseDto> schedules = scheduleService.getSchedules(page, size);
        return ResponseEntity.ok(schedules);
    }

    // 스케줄 업데이트 - 스케줄 작성자만 수정 가능
    @PutMapping("/update/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.updateSchedule(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 스케줄 삭제 - 스케줄 작성자만 삭제 가능
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
