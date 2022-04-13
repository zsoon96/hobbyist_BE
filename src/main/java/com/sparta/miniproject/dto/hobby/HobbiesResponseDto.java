package com.sparta.miniproject.dto.hobby;

import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.timeconversion.TimeConversion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HobbiesResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String img;
    private String date;
    private int commentsCount;

    public HobbiesResponseDto(Hobby hobby){
        this.id = hobby.getId();
        this.nickname = hobby.getUser().getNickname();
        this.title = hobby.getTitle();
        this.img = hobby.getImg();
        this.date = TimeConversion.timeConversion(hobby.getModifiedAt());
        this.commentsCount = hobby.getCommentsCount();
    }
}