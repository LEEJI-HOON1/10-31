package com.sparta.schedule.service;

import com.sparta.schedule.auth.JwtTokenProvider;
import com.sparta.schedule.dto.CommentRequestDto;
import com.sparta.schedule.dto.CommentResponseDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final JwtTokenProvider jwtTokenProvider;
    private final CommentRepository commentRepository;

    // 댓글 생성 - 로그인한 사용자 정보로 댓글 생성
    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        User loginedUser = jwtTokenProvider.getLoginedUser();
        Comment comment = new Comment(requestDto, loginedUser);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);
    }

    // 댓글 조회
    public List<CommentResponseDto> getComments() {
        return commentRepository.findAll().stream().map(CommentResponseDto::new).toList();
    }

    // 댓글 업데이트 - 댓글 작성자만 수정 가능
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        User loginedUser = jwtTokenProvider.getLoginedUser();
        Comment comment = findComment(id);
        if (!comment.getUser().equals(loginedUser)) {
            throw new RuntimeException("댓글 작성자만 수정할 수 있습니다.");
        }
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제 - 댓글 작성자만 삭제 가능
    @Transactional
    public void deleteComment(Long id) {
        User loginedUser = jwtTokenProvider.getLoginedUser();
        Comment comment = findComment(id);
        if (!comment.getUser().equals(loginedUser)) {
            throw new RuntimeException("댓글 작성자만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }
}
