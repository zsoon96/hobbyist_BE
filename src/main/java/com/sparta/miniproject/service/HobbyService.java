package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.HobbyRequestDto;
import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.HobbyRepository;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HobbyService {
    
    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;

    // 게시글 생성
    public StatusResponseDto createHobby(HobbyRequestDto requestDto, UserDetailsImpl userDetails){

        // 유효성 검사 시행 필요( 내용 확인, 유저 로그인 상태 검증 )

        // 회원정보에 따라 HobbyRequestDto의 nickname을 Set
//        requestDto.setNickname(userRepository.findByUsername(userDetails.getUsername())
//                .orElseThrow(()-> new NullPointerException("접근 권한이 없습니다.")).toString());

        // 저장 및 반환
        return new StatusResponseDto(hobbyRepository.save(new Hobby(userDetails, requestDto)));
    }
}