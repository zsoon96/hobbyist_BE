package com.sparta.miniproject.controller.comment;

import com.sparta.miniproject.dto.StatusResponseDto;
import com.sparta.miniproject.dto.comment.CommentRequestDto;
import com.sparta.miniproject.dto.comment.CommentResponseDto;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;


    // 댓글 생성 - User만 생성가능
    @PostMapping("/comment/{hobbyId}")
    public StatusResponseDto creatComment (@PathVariable Long hobbyId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.postComment(hobbyId,commentRequestDto,userDetails);
    }

    // 댓글 조회 - 모두 조회가능
    @GetMapping("/hobbies/{hobbyId}/comments")
    public List<CommentResponseDto> showComment (@PathVariable Long hobbyId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.getComment(hobbyId, userDetails);
    }

    // 댓글 수정
    @PutMapping("/comment/{commentId}")
    public StatusResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) { // User만 수정가능
        return commentService.putComment(commentId, commentRequestDto, userDetails);
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public StatusResponseDto deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(commentId, userDetails);
    }
}