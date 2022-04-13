package com.sparta.miniproject.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String comment;
    private Long hobbyId;
}
