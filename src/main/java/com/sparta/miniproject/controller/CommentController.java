package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성 - User만 생성가능
    @PostMapping("/api/comment/{hobbyId}")
    public Comment creatComment (@PathVariable Long hobbyId, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.postComment(commentRequestDto);
    }

//     댓글 조회 - 모두 조회가능
    @GetMapping("/api/hobbies/{hobbyId}/comments")
    public List<Comment> showComment (@PathVariable Long hobbyId) {
        return commentService.getComment(hobbyId);
    }

    // 댓글 수정
    @PutMapping("/api/comment/{commentId}")
    public Long updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) { // User만 수정가능
        commentService.putComment(commentId, commentRequestDto);
        return commentId;
    }

    // 댓글 삭제
    @DeleteMapping("/api/comment/{commentId}")
    public Long deleteComment(@PathVariable Long commentId) { // User만 삭제가능
        commentService.deleteComment(commentId);
        return commentId;
    }
}
