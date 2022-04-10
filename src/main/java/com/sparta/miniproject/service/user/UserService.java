package com.sparta.miniproject.service.user;

import com.sparta.miniproject.dto.user.SignupRequestDto;
import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String pattern = "^[a-zA-Z0-9]*$";

    // 회원가입
    public StatusResponseDto signup(SignupRequestDto requestDto){

        // 유효성 검사 시행
        isValid(requestDto);

        // 비밀번호 암호화
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        // 유효성 검사가 종료됐을 시, 저장한 뒤 결과값을 전달해 줍니다.
        return new StatusResponseDto(userRepository.save(new User(requestDto)));
    }

    // 유효성 검사 메소드
    private void isValid(SignupRequestDto requestDto){

        // 유효성 검사 시작
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();

        // 아이디 유효성 검사
        if ( !Pattern.matches(pattern, username) || username.length() < 3 ) {
            throw new IllegalArgumentException("아이디 입력을 확인해주세요.");
        }
        // 비밀번호 유효성 검사
        if ( ( username.contains(password) || password.length() < 4 ) || !password.equals(passwordCheck) ) {
            throw new IllegalArgumentException("비밀번호 입력을 확인해주세요.");
        }
        // 회원 ID 중복 확인
        Optional<User> foundUsername = userRepository.findByUsername(username);
        if (foundUsername.isPresent()) {
            throw new IllegalArgumentException("아이디가 중복됩니다.");
        }
        // 회원 닉네임 중복 확인
        Optional<User> foundNickname = userRepository.findByNickname(nickname);
        if (foundNickname.isPresent()) {
            throw new IllegalArgumentException("닉네임이 중복됩니다.");
        }
    }

}
