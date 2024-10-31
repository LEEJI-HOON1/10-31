package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{


}
