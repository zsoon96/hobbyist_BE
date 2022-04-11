package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Hobby;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class StatusResponseDto {
    private String status = String.valueOf(HttpStatus.OK);
    private String message;

    public StatusResponseDto(Comment comment) {
        this.message = "댓글 작성에 성공했습니다.";
    }

    public StatusResponseDto(Hobby hobby) {
        this.message = "게시물 생성을 완료했습니다.";
    }


    public StatusResponseDto(CommentRequestDto commentRequestDto) {
        this.message = "댓글 수정에 성공했습니다.";
    }
}
