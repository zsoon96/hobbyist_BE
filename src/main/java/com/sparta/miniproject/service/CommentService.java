package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 생성 로직 - User만 생성가능
    @Transactional
    public Comment postComment(CommentRequestDto commentRequestDto) {
        // user 확인 필요
        Comment comment = new Comment(commentRequestDto);
        commentRepository.save(comment);
        return comment;
    }

    // 댓글 조회 로직 - 모두 조회가능
    public List<Comment> getComment(@PathVariable Long hobbyId) {
        return commentRepository.findByHobbyIdOrderByModifiedAtDesc(hobbyId);
    }

    // 댓글 수정 로직
    @Transactional
    public void putComment(Long commentId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("")
        );
        comment.updateComment(commentRequestDto);
    }

    // 댓글 삭제 로직
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
