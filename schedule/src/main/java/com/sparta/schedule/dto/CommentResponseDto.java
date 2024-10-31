package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long userId; // 댓글 작성자 ID
    private Long scheduleId; // 댓글이 달린 스케줄 ID

    // Comment 엔티티를 기반으로 CommentResponseDto 생성
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.scheduleId = comment.getSchedule().getId();

    }
}
