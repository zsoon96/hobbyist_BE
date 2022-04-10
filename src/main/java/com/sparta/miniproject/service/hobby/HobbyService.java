package com.sparta.miniproject.service.hobby;

import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.dto.hobby.HobbyDetailResponseDto;
import com.sparta.miniproject.dto.hobby.HobbyRequestDto;
import com.sparta.miniproject.dto.hobby.HobbiesResponseDto;
import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.repository.HobbyRepository;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class HobbyService {

    @Autowired
    private HobbyRepository hobbyRepository;
    @Autowired
    private UserRepository userRepository;

    // 게시글 생성
    public StatusResponseDto createHobby(HobbyRequestDto requestDto, UserDetailsImpl userDetails){

        // 유효성 검사 시행 필요( 내용 확인, 유저 로그인 상태 검증 )

        // 회원정보에 따라 HobbyRequestDto의 nickname을 Set
        requestDto.setNickname(userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()-> new NullPointerException("접근 권한이 없습니다.")).getNickname());

        // 저장 및 반환
        return new StatusResponseDto(hobbyRepository.save(new Hobby(userDetails, requestDto)));
    }

    // 게시글 수정
    @Transactional
    public StatusResponseDto update(Long hobbyId, HobbyRequestDto requestDto, UserDetailsImpl userDetails) {

        // 유효성 검사 시행 필요 ( 내용 확인 및 회원 일치여부 검사 )
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        if ( !hobby.getUser().getUsername().equals(userDetails.getUsername())){
            throw new NullPointerException("접근 권한이 없습니다."); // 아직 적절한 오류 구문을 찾지 못하였습니다. 찾아서 수정해 주도록 합니다.
        }

        // 게시글 업데이트 시행 및 반환
        hobby.update(requestDto);
        return new StatusResponseDto(hobbyId, hobby);
    }

    // 게시글 삭제
    public StatusResponseDto deleteHobby(Long hobbyId, UserDetailsImpl userDetails){

        // 유효성 검사 시행 필요 ( 회원 일치여부 검사 )
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));
        if ( !userRepository.findByHobbies(hobby).getUsername().equals(userDetails.getUsername()) ){
            throw new NullPointerException("접근 권한이 없습니다."); // 아직 적절한 오류 구문을 찾지 못하였습니다. 찾아서 수정해 주도록 합니다.
        };
        hobbyRepository.deleteById(hobbyId);
        return new StatusResponseDto(hobbyId);
    }

    // 게시글 전체 목록 조회
    public List<HobbiesResponseDto> getHobbies(){

        List<Hobby> hobbies = hobbyRepository.findAllByOrderByModifiedAtDesc();
        List<HobbiesResponseDto> responseDtos = new ArrayList<>();

        for ( Hobby hobby : hobbies ){
            responseDtos.add(new HobbiesResponseDto(hobby));
        }

        return responseDtos;
    }

    // 게시글 상세 조회
    public HobbyDetailResponseDto getHobby(Long hobbyId, UserDetailsImpl userDetails){

        // 게시글 검색
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));

        // 게시글 상세 조회
        return new HobbyDetailResponseDto(hobby,
                userRepository.findByHobbies(hobby).getUsername().equals(userDetails.getUsername()));
    }
}