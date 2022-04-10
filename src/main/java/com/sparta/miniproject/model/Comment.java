package com.sparta.miniproject.model;

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
    private User user;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn
    private Hobby hobby; // 개수가 세어지는지 확인이 필요합니다.
}
