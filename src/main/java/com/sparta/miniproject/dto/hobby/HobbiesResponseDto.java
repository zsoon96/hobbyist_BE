package com.sparta.miniproject.dto.hobby;

import com.sparta.miniproject.model.Hobby;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HobbiesResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String img;
    private String date;
    private int commentsCount;

    public HobbiesResponseDto(Hobby hobby, String timeConversion){
        this.id = hobby.getId();
        this.nickname = hobby.getUser().getNickname();
        this.title = hobby.getTitle();
        this.img = hobby.getImg();
        this.date = timeConversion;
        this.commentsCount = hobby.getCommentsCount();
    }
}