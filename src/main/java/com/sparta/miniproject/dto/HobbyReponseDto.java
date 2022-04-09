package com.sparta.miniproject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class HobbyReponseDto {
    private String title;
    private Long hobbyId;
    private String img;
    private String modifiedAt;
    private String url;
}
