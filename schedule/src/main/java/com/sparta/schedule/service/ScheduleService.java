package com.sparta.schedule.service;

import com.sparta.schedule.auth.JwtTokenProvider;
import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final JwtTokenProvider jwtTokenProvider;
    private final ScheduleRepository scheduleRepository;

    // 스케줄 생성 - 로그인한 사용자 정보로 생성
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        User loginedUser = jwtTokenProvider.getLoginedUser();
        Schedule schedule = new Schedule(requestDto, loginedUser);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    // 스케줄 조회 (페이징)
    public Page<ScheduleResponseDto> getSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleRepository.findAll(pageable).map(ScheduleResponseDto::new);
    }

    // 스케줄 업데이트 - 스케줄 작성자만 수정 가능
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        User loginedUser = jwtTokenProvider.getLoginedUser();
        Schedule schedule = findSchedule(id);
        if (!schedule.getUser().equals(loginedUser)) {
            throw new RuntimeException("스케줄 작성자만 수정할 수 있습니다.");
        }
        schedule.update(requestDto);
        return new ScheduleResponseDto(schedule);
    }

    // 스케줄 삭제 - 스케줄 작성자만 삭제 가능
    @Transactional
    public void deleteSchedule(Long id) {
        User loginedUser = jwtTokenProvider.getLoginedUser();
        Schedule schedule = findSchedule(id);
        if (!schedule.getUser().equals(loginedUser)) {
            throw new RuntimeException("스케줄 작성자만 삭제할 수 있습니다.");
        }
        scheduleRepository.delete(schedule);
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 스케줄은 존재하지 않습니다.")
        );
    }
}
