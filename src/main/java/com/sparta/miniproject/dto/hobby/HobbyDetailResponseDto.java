package com.sparta.miniproject.dto.hobby;

import com.sparta.miniproject.model.Hobby;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HobbyDetailResponseDto {
    private Long id;
    private String img;
    private String title;
    private String nickname;
    private String content;
    private String url;
    private String date;
    private boolean usable;

    public HobbyDetailResponseDto(Hobby hobby, boolean usable, String timeConversion){
        this.id = hobby.getId();
        this.img = hobby.getImg();
        this.title = hobby.getTitle();
        this.nickname = hobby.getUser().getNickname();
        this.content = hobby.getContent();
        this.url = hobby.getUrl();
        this.date = timeConversion;
        this.usable = usable;
    }
}
