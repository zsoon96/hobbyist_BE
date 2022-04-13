package com.sparta.miniproject.dto.hobby;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HobbyRequestDto {
    private String nickname;
    private String img; // 이미지 경로는 기본 경로로 초기화 시켜 둬야 합니다. 이에 대해서 협의하도록 합니다.
    private String title;
    private String content;
    private String url;
}