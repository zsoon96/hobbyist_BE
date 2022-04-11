package com.sparta.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HobbyRequestDto {
    private String nickname;
    private String img;
    private String title;
    private String content;
    private String url;
}