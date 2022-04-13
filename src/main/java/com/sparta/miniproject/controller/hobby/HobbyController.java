package com.sparta.miniproject.controller.hobby;

import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.dto.hobby.HobbyDetailResponseDto;
import com.sparta.miniproject.dto.hobby.HobbyRequestDto;
import com.sparta.miniproject.dto.hobby.HobbiesResponseDto;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.hobby.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HobbyController {

    @Autowired
    private HobbyService hobbyService;

    // 게시글 등록
    @PostMapping("/hobby")
    public StatusResponseDto createHobby(@RequestBody HobbyRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hobbyService.createHobby(requestDto, userDetails);
    }

    // 게시글 수정
    @PutMapping("/hobby/{hobbyId}")
    public StatusResponseDto updateHobby(@PathVariable Long hobbyId,
                                         @RequestBody HobbyRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hobbyService.update(hobbyId, requestDto, userDetails);
    }

    // 게시글 삭제
    @DeleteMapping("/hobby/{hobbyId}")
    public StatusResponseDto deleteHobby(@PathVariable Long hobbyId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hobbyService.deleteHobby(hobbyId, userDetails);
    }

    // 게시글 전체 조회
    @GetMapping("/hobbies")
    public List<HobbiesResponseDto> readHobbies(){
        return hobbyService.getHobbies();
    }

    // 게시글 상세 조회
    @GetMapping("/hobbies/{hobbyId}")
    public HobbyDetailResponseDto readHobby(@PathVariable Long hobbyId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hobbyService.getHobby(hobbyId, userDetails);
    }

}