package com.sparta.miniproject.dto.user;

import com.sparta.miniproject.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class AuthResponseDto {
    private String username;
    private String nickname;
    private final String status = "true";
    private final String http = String.valueOf(HttpStatus.OK);

    public AuthResponseDto(User user){
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}
