package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.SignupRequestDto;
import com.sparta.miniproject.dto.SignupResponseDto;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public SignupResponseDto signup(SignupRequestDto requestDto){
        // 유효성 검사 시행이 필요합니다.

        // 유효성 검사가 종료됐을 시, 저장한 뒤 결과값을 전달해 줍니다.
        return new SignupResponseDto(userRepository.save(new User(requestDto)));
    }
}
