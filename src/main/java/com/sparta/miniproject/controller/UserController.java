package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.SignupRequestDto;
import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
