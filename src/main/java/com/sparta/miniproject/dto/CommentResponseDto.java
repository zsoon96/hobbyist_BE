package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String comment;
//    private String nickname;
    private LocalDateTime date;
    private boolean usable;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
//        this.nickname = comment.getUser().getNickname();
        this.date= comment.getModifiedAt();
    }
}
