package com.sparta.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HobbyRequestDto {
    private String title;
    private String img;
    private String username;
    private String url;
    private String content;
}
