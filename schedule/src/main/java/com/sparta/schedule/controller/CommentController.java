package com.sparta.schedule.controller;

import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/create")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.createComment(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 댓글 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<CommentResponseDto> comments = commentService.getComments();
        return ResponseEntity.ok(comments);
    }

    // 댓글 업데이트 - 댓글 작성자만 수정 가능
    @PutMapping("/update/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.updateComment(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 댓글 삭제 - 댓글 작성자만 삭제 가능
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
