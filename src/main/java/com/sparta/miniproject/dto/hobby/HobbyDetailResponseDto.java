package com.sparta.miniproject.dto.hobby;

import com.sparta.miniproject.model.Hobby;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HobbyDetailResponseDto {
    private Long id;
    private String img;
    private String title;
    private String nickname;
    private String content;
    private String url;
    private LocalDateTime date;
    // 회원 여부에 따른 게시글 조작 가능 여부를 판별하기 위한 변수입니다. 팀원과 협의가 필요합니다.
    private boolean isUsable;

    // 게시글 작성자와 유저 정보 일치여부를 추가적으로 받을 변수가 필요합니다.
    public HobbyDetailResponseDto(Hobby hobby, boolean isUsable){
        this.id = hobby.getId();
        this.img = hobby.getImg();
        this.title = hobby.getTitle();
        this.nickname = hobby.getUser().getNickname();
        this.content = hobby.getContent();
        this.url = hobby.getUrl();
        this.date = hobby.getModifiedAt();
        this.isUsable = isUsable;
    }
}
