package com.sparta.miniproject.service.comment;

import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.dto.comment.CommentRequestDto;
import com.sparta.miniproject.dto.comment.CommentResponseDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Hobby;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.HobbyRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.utils.time.TimeConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private HobbyRepository hobbyRepository;
    @Autowired
    private TimeConversion timeConversion;

    // 댓글 생성 로직 - User만 생성가능
    public StatusResponseDto postComment(Long hobbyId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        // 로그인 여부 검증
        try {
            userDetails.getUsername();
        } catch (Exception e) {
            throw new IllegalArgumentException("접근권한이 없습니다.");
        }

        // hobbyId로 Repo에서 해당 hooby 객체를 찾아 넣은 'hobby' 객체를 생성
        Hobby hobby = hobbyRepository.findById(hobbyId).orElseThrow(()-> new IllegalArgumentException("게시글이 없습니다."));
        // userDetails에서 유저 정보를 찾아 user 객체에 담아줌
        User user = userDetails.getUser();
        // 받아온 hooby 객체와 commentDto, user 객체를 comment 객체에 담아줌
        Comment comment = new Comment(hobby, commentRequestDto, user);

        return new StatusResponseDto(commentRepository.save(comment));
    }

    // 댓글 조회 로직 - 모두 조회가능
    public List<CommentResponseDto> getComment(Long hobbyId, UserDetailsImpl userDetails) {
        Hobby hobby = hobbyRepository.findById(hobbyId)
                .orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        // hobbyId를 통해 해당 게시글의 comment들 repo에서 불러오기
        List<Comment> commentList = commentRepository.findAllByHobbyOrderByModifiedAtDesc(hobby); // comment 리스트
        // comment에 nickname까지 담아서 응답해줄 commentResponseDto 리스트 생성 후 초기화
        List<CommentResponseDto> commentResonseDto = new ArrayList<>();

        try {
            for (Comment comment : commentList) {
                CommentResponseDto responseDto = new CommentResponseDto(
                        comment,
                        comment.getUser().getUsername().equals(userDetails.getUsername()),
                        timeConversion.commentConversion(comment.getModifiedAt()));
                commentResonseDto.add(responseDto);
            }
        } catch (Exception e) {
            for (Comment comment : commentList) {
                CommentResponseDto responseDto = new CommentResponseDto(
                        comment,
                        false,
                        timeConversion.commentConversion(comment.getModifiedAt()));
                commentResonseDto.add(responseDto);
            }
        }

        return commentResonseDto;
    }

    // 댓글 수정 로직 - User, 작성자만 수정가능
    @Transactional
    public StatusResponseDto putComment(Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 없습니다.")
        );

        // 수정 권한 확인
        try{
            if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
                throw new IllegalArgumentException("작성자만 수정 가능합니다.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("접근권한이 없습니다.");
        }
        comment.updateComment(commentRequestDto);

        return new StatusResponseDto(commentRequestDto);
    }

    // 댓글 삭제 로직 - User, 작성자만 수정가능
    public StatusResponseDto deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 없습니다.")
        );
        try{
            if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
                throw new IllegalArgumentException("작성자만 삭제 가능합니다.");
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("접근권한이 없습니다.");
        }

        commentRepository.deleteById(commentId);

        return new StatusResponseDto(commentId, comment);
    }
}
