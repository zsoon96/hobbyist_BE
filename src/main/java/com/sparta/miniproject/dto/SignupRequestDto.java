package com.sparta.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String loginId;
    private String nickName;
    private String password;
}
