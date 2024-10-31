package com.sparta.schedule.entity;

import com.sparta.schedule.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(CommentRequestDto requestDto, User user) {
        this.content = requestDto.getContent();
        this.user = user;
        this.schedule = schedule;
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
