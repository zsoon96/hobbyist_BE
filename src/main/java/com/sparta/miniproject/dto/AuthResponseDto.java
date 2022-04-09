package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthResponseDto {
    private String username;
    private String nickname;
    private String status;

    public AuthResponseDto(User user){
        this.username = user.getUsername();
        this.nickname = user.getNickname();

    }
}
