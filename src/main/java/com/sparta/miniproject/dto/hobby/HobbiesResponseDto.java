package com.sparta.miniproject.dto.hobby;

import com.sparta.miniproject.model.Hobby;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HobbiesResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String img;
    private LocalDateTime date;
    private int commentsCount;

    public HobbiesResponseDto(Hobby hobby){
        this.id = hobby.getId();
        this.nickname = hobby.getUser().getNickname();
        this.title = hobby.getTitle();
        this.img = hobby.getImg();
        this.date = hobby.getModifiedAt();
        this.commentsCount = hobby.getCommentsCount();
    }
}