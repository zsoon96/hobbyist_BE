package com.sparta.miniproject.service.hobby;

import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.dto.hobby.HobbyDetailResponseDto;
import com.sparta.miniproject.dto.hobby.HobbyRequestDto;
import com.sparta.miniproject.dto.hobby.HobbiesResponseDto;
import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.repository.HobbyRepository;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.utils.time.TimeConversion;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private TimeConversion timeConversion;
    @Value("${url.path}")
    private String preAddress;

    // 게시글 생성
    @Transactional
    public StatusResponseDto createHobby(HobbyRequestDto requestDto, UserDetailsImpl userDetails) {

        // 유효성 검사 시행 필요( 내용 확인, 유저 로그인 상태 검증 )
        requestDto = isValidCreateValue(requestDto, userDetails);

        // 회원정보에 따라 HobbyRequestDto의 nickname을 Set
        requestDto.setNickname(userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()-> new NullPointerException("로그인을 확인해 주세요.")).getNickname());

        //
        if ( requestDto.getImg() == null ){
            return new StatusResponseDto(hobbyRepository.save(new Hobby(userDetails, requestDto)));
        } else {
            // 이미지 저장 및 업데이트 시행
            Hobby hobby = hobbyRepository.save(new Hobby(userDetails, requestDto));
            requestDto = uploadImage(requestDto, hobby.getId());
            hobby.update(requestDto);
            return new StatusResponseDto(hobby);
        }
    }

    // 게시글 수정
    @Transactional
    public StatusResponseDto update(Long hobbyId, HobbyRequestDto requestDto, UserDetailsImpl userDetails) {

        // 유효성 검사 시행 필요 ( 내용 확인 및 회원 일치여부 검사 )
        isValidCreateValue(requestDto, userDetails);

        // 게시글 확인
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다.")
        );

        // 이미지 변경시 업데이트 시행
        if ( requestDto.getImg() != null ){
            deleteImage(hobbyId);
            uploadImage(requestDto, hobbyId);
        }

        // 게시글 업데이트 시행 및 반환
        hobby.update(requestDto);
        return new StatusResponseDto(hobbyId, hobby);
    }

    // 게시글 삭제
    public StatusResponseDto deleteHobby(Long hobbyId, UserDetailsImpl userDetails){

        // 유효성 검사 시행 필요 ( 회원 일치여부 검사 )
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        if ( !userRepository.findByHobbies(hobby).getUsername().equals(userDetails.getUsername()) ){
            throw new NullPointerException("접근 권한이 없습니다."); // 아직 적절한 오류 구문을 찾지 못하였습니다. 찾아서 수정해 주도록 합니다.
        };
        // 이미지 삭제
        deleteImage(hobbyId);

        // 게시글 삭제
        hobbyRepository.deleteById(hobbyId);
        return new StatusResponseDto(hobbyId);
    }

    // 게시글 전체 목록 조회
    public List<HobbiesResponseDto> getHobbies(){

        List<Hobby> hobbies = hobbyRepository.findAllByOrderByModifiedAtDesc();
        List<HobbiesResponseDto> responseDtos = new ArrayList<>();

        for ( Hobby hobby : hobbies ){
            responseDtos.add(new HobbiesResponseDto(
                    hobby,
                    timeConversion.timeConversion(hobby.getModifiedAt())));
        }

        return responseDtos;
    }

    // 게시글 상세 조회
    public HobbyDetailResponseDto getHobby(Long hobbyId, UserDetailsImpl userDetails){

        boolean usable;
        // 게시글 검색
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        // 게시글 상세 조회
        try {
            usable = userRepository.findByHobbies(hobby).getUsername().equals(userDetails.getUsername());
        } catch (Exception e) {
            usable = false;
        }
        return new HobbyDetailResponseDto(
                hobby,
                usable,
                timeConversion.detailConversion(hobby.getModifiedAt()));
    }

    // 게시글 등록 유효성 검증
    private HobbyRequestDto isValidCreateValue(HobbyRequestDto requestDto, UserDetailsImpl userDetails){

        // 로그인 검증
        try {
            userDetails.getUser().getUsername();
        } catch (Exception e) {
            throw new IllegalArgumentException("로그인을 확인해 주세요.");
        }
        // 내용 검증
        if ( requestDto.getContent().trim().equals("")) {
            throw new IllegalArgumentException("내용 입력을 확인해 주세요.");
        }
        return requestDto;
    }

    // 이미지 등록
    private HobbyRequestDto uploadImage(HobbyRequestDto requestDto, Long id){

        String encodedImage = requestDto.getImg();

        // Base64를 디코딩해서 이미지 파일로 만들어 줍니다.
        String imageName = String.valueOf(id) + ".jpg";
        String path = "../images/";

        try {
            // 폴더를 생성합니다.
            File folder = new File(path);
            if (!folder.exists()) { folder.mkdir(); }

            // base64 인코딩 스트링을 저장
            String imageBase64 = encodedImage.split(",")[1];
            byte[] decode = Base64.decode(imageBase64);
            FileOutputStream fos;

            // 이미지 파일 저장
            File file = new File(path + imageName); // 해당 경로에 파일을 생성한다. 아직 비어있다.
            fos = new FileOutputStream(file);
            fos.write(decode); // 이를 통해 생성된 파일의 내용을 작성한다.
            fos.close(); // 파일을 닫는다.

            // 이미지 주소 지정(꼭 설정해 주도록 합니다.)
            requestDto.setImg(preAddress + "images/" + imageName);

            return requestDto;

        } catch (IOException e){ // IOException에 대해서 반드시 예외처리를 해야 합니다.
            throw new IllegalArgumentException("이미지 파일의 형식이 올바르지 않습니다.");
        }
    }

    // 이미지 삭제
    private void deleteImage(Long id){
        String imageName = String.valueOf(id) + ".jpg";
        String path = "../images/";

        File deleteFile = new File(path + imageName);

        if ( deleteFile.exists() ){ deleteFile.delete(); }
    }
}