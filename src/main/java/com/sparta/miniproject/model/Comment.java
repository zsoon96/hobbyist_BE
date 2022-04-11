package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.comment.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Hobby hobby; // 개수가 세어지는지 확인이 필요합니다.

    @Column(nullable = false)
    private String comment;

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

    public Comment(Hobby hobby, CommentRequestDto commentRequestDto, User user) {
        this.hobby = hobby;
        this.comment = commentRequestDto.getComment();
        this.user = user;
    }
}
