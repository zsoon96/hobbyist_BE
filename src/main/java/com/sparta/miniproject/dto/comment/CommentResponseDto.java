package com.sparta.miniproject.dto.comment;

import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.timeconversion.TimeConversion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String nickname;
    private String date;
    private boolean usable;

    public CommentResponseDto(Comment comment, boolean usable) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.nickname = comment.getUser().getNickname();
        this.date= TimeConversion.timeConversion(comment.getModifiedAt());
        this.usable = usable;
    }

}
