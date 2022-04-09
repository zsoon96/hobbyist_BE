package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class StatusResponseDto {
    private String status = String.valueOf(HttpStatus.OK);
    private String message;

    public StatusResponseDto(User user){
        this.message = "회원가입에 성공했습니다.";
    }
}
