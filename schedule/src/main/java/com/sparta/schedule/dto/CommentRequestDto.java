package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentRequestDto {

    @NotBlank(message = " 이름을 입력해주세요.")
    @Size(max = 8 , message = "8글자 이하로 입력해주세요.")
    private String username;
    @NotBlank(message = " 내용을 입력해주세요.")
    @Size(max = 50 , message = "50 글자 이하만 입력가능합니다.")
    private String content;
}
