package com.sparta.miniproject.service.hobby;

import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.dto.hobby.HobbyDetailResponseDto;
import com.sparta.miniproject.dto.hobby.HobbyRequestDto;
import com.sparta.miniproject.dto.hobby.HobbiesResponseDto;
import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.repository.HobbyRepository;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HobbyService {

    @Autowired
    private HobbyRepository hobbyRepository;
    @Autowired
    private UserRepository userRepository;

    // 게시글 생성
    public StatusResponseDto createHobby(HobbyRequestDto requestDto, UserDetailsImpl userDetails) {

        // 유효성 검사 시행 필요( 내용 확인, 유저 로그인 상태 검증 )
        requestDto = isValidCreateValue(requestDto, userDetails);

        // 회원정보에 따라 HobbyRequestDto의 nickname을 Set
        requestDto.setNickname(userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()-> new NullPointerException("접근 권한이 없습니다.")).getNickname());

        // DB 저장 및 반환
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

    // 게시글 등록 유효성 검증
    private HobbyRequestDto isValidCreateValue(HobbyRequestDto requestDto, UserDetailsImpl userDetails){

        // 로그인 검증
        try{
            System.out.println(userDetails.getUser().getUsername());
        } catch (Exception e){
            throw new IllegalArgumentException("로그인을 확인해 주세요.");
        }

        ////////////////////////////////////////////////////////////////
        // Base64 image파일 디코딩 및 디렉토리 설정, DB에는 주소만 할당해 저장 //
        ////////////////////////////////////////////////////////////////

        // 전송한 이미지가 있을 시에 수행합니다. 이에 대한 판별이 필요합니다.
        String encodedImage = requestDto.getImg();

        if ( encodedImage != null ){
            // Base64를 디코딩해서 이미지 파일로 만들기
            String imageName = "image.jpg";
            String prePath = "C:\\Users\\unbea\\Desktop\\";
            try {
                String imageBase64 = encodedImage.split(",")[1]; // base64 스트링을 저장
                byte[] decode = Base64.decode(imageBase64);
                FileOutputStream fos;
                // 만들어진 이미지 파일을 저장할 디렉토리 생성
                // 이미지 파일 저장
                File path = new File(prePath + imageName); // 해당 경로에 파일을 생성한다. 아직 비어있다.
                fos = new FileOutputStream(path);
                fos.write(decode); // 이를 통해 생성된 파일의 내용을 작성한다.
                fos.close(); // 파일을 닫는다.

                // requestDto에 이미지 저장 경로로 할당

            } catch (IOException e){ // IOException에 대해서 반드시 예외처리를 해야 합니다.

                throw new IllegalArgumentException("이미지 파일의 형식이 올바르지 않습니다.");

            }
        }
        requestDto.setImg("basic address");
        return requestDto;
    }
}