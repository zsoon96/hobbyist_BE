package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.HobbyReponseDto;
import com.sparta.miniproject.dto.HobbyRequestDto;
import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.repository.HobbyRepository;
import com.sparta.miniproject.timeconversion.TimeConversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class HobbyService {
    private final HobbyRepository hobbyRepository;

    //게시물 저장
    @Transactional
    public HobbyReponseDto createhobby(HobbyRequestDto requestDto){
        Hobby hobby = new Hobby(requestDto);
        hobbyRepository.save(hobby);

        return new HobbyReponseDto(
                hobby.getTitle(),
                hobby.getId(),
                hobby.getImg(),
                TimeConversion.timeConversion(hobby.getModifiedAt()),
                hobby.getUrl()
        );
    }

    @Transactional
    public Long update(Long hobbyId, HobbyRequestDto requestDto){
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        hobby.update(requestDto);
        return  hobby.getId();
    }

    @Transactional
    public Long deletehobby(Long hobbyId){
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(() -> new IllegalArgumentException("게시물이 없습니다."));
        hobbyRepository.deleteById(hobbyId);
        return hobbyId;
    }
}
