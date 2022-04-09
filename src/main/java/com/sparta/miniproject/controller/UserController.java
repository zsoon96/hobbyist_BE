package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.AuthResponseDto;
import com.sparta.miniproject.dto.SignupRequestDto;
import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/signup")
    public StatusResponseDto signup(@RequestBody SignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

    @GetMapping("/user/auth")
    public AuthResponseDto getAuth(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new AuthResponseDto(userDetails.getUser());
    }

}
