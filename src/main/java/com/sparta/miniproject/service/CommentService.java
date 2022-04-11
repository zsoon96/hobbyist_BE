package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.dto.CommentResponseDto;
import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.HobbyRepository;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final HobbyRepository hobbyRepository;
    private final UserRepository userRepository;

    // 댓글 생성 로직 - User만 생성가능
    public StatusResponseDto postComment(Long hobbyId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        // hobbyId로 Repo에서 해당 hobby 객체를 찾아 넣은 'hobby' 객체를 생성
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(()-> new IllegalArgumentException("게시글이 없습니다."));
        // userDetails에서 유저 정보를 찾아 user 객체에 담아줌
        User user = userDetails.getUser();
        // 받아온 hooby 객체와 commentDto, user 객체를 comment 객체에 담아줌
        Comment comment = new Comment(hobby,commentRequestDto,user);
        commentRepository.save(comment);
        StatusResponseDto statusResponseDto = new StatusResponseDto(comment);
        return statusResponseDto;
    }

    // 댓글 조회 로직 - 모두 조회가능
    public List<CommentResponseDto> getComment(Long hobbyId) {
        // hobbyId를 통해 해당 게시글의 comment들 repo에서 불러오기
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(()->new IllegalArgumentException(""));
        List<Comment> commentList = commentRepository.findByHobbyOrderByModifiedAtDesc(hobby); // comment 리스트
        // comment에 nickname까지 담아서 응답해줄 commentResponseDto 리스트 생성 후 초기화
        List<CommentResponseDto> commentResponseDto = new ArrayList<>();

        // comment 리스트를 반복문을 통해 하나씩 빼서 ResponseDto에 담아주고
        // commentResponseDto 리스트에 추가 후 반환
        for (Comment comment : commentList) {
            CommentResponseDto responseDto = new CommentResponseDto(comment);
            commentResponseDto.add(responseDto);
        }

        return commentResponseDto;
    }

    // 댓글 수정 로직 - User, 작성자만 수정가능
    @Transactional
    public StatusResponseDto putComment(Long commentId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 없습니다.")
        );

        // 수정 권한 확인
//        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
//            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
//        }

        comment.updateComment(commentRequestDto);
        StatusResponseDto statusResponseDto = new StatusResponseDto(commentRequestDto);
        return statusResponseDto;
    }

    // 댓글 삭제 로직 - User, 작성자만 수정가능
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 없습니다.")
        );

//        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
//            throw new IllegalArgumentException("작성자만 삭제 가능합니다.");
//        }

        commentRepository.deleteById(commentId);
    }
}
