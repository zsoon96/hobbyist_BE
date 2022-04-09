package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class StatusResponseDto {
    private String status = String.valueOf(HttpStatus.OK);
    private String message;

    public StatusResponseDto(Comment comment){
        this.message = "댓글 작성에 성공했습니다.";
    }
}
