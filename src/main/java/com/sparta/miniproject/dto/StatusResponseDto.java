package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class StatusResponseDto {

    private String status = String.valueOf(HttpStatus.OK);
    private String message;

    public StatusResponseDto(User user){
        this.message = "회원가입에 성공했습니다.";
    }

    public StatusResponseDto(Hobby hobby){
        this.message = "게시물 생성을 완료했습니다.";
    }

    public StatusResponseDto(Long hobbyId, Hobby hobby){
        this.message = "게시물을 수정했습니다.";
    }

    // 매개변수가 겹쳐질 수 있으므로, 수정 작업이 필요합니다.
    public StatusResponseDto(Long hobbyId){
        this.message = "게시물을 삭제했습니다.";
    }

    public StatusResponseDto(Comment comment){
        this.message = "댓글 작성을 완료했습니다.";
    }

    public StatusResponseDto(Long commentId, Comment comment){
        this.message = "댓글을 수정했습니다.";
    }

}
