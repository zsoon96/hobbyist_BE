package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.HobbyRequestDto;
import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HobbyController {

    private final HobbyService hobbyService;

    // 게시글 등록
    @PostMapping("api/hobby")
    public StatusResponseDto createHobby(@RequestBody HobbyRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hobbyService.createHobby(requestDto, userDetails);
    }
}
