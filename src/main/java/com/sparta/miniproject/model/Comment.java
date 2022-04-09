package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.CommentRequestDto;
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

    @Column(nullable = false)
    private Long hobbyId;

//    @ManyToOne
//    @JoinColumn(name = "hobbyId")
//    private Hobby hobby;
//
//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private User user;



    public Comment(Long hobbyId, CommentRequestDto commentRequestDto) {
        this.hobbyId = hobbyId;
        this.comment = commentRequestDto.getComment();
    }
}
