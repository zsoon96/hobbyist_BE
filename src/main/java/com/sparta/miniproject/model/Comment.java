package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

//    @Column(nullable = false)
//    private Long hobbyId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Hobby hobby;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = true)
    private User user;


    public void updateComment(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

    public Comment(Hobby hobby, CommentRequestDto commentRequestDto, User user) {
        this.hobby = hobby;
        this.comment = commentRequestDto.getComment();
        this.user = user;
    }
}
