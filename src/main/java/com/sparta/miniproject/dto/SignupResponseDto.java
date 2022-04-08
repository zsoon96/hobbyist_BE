package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupResponseDto {
    private String ok;
    private String message;

    public SignupResponseDto(User user){
        this.ok = "true";
        this.message = "회원가입 성공";
    }
}
