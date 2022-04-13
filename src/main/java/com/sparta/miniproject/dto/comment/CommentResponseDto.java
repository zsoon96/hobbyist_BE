package com.sparta.miniproject.dto.comment;

import com.sparta.miniproject.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String nickname;
    private String date;
    private boolean usable;

    public CommentResponseDto(Comment comment, boolean usable, String timeConversion) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.nickname = comment.getUser().getNickname();
        this.date = timeConversion;
        this.usable = usable;
    }

}
