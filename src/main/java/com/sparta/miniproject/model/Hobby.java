package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.hobby.HobbyRequestDto;
import com.sparta.miniproject.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Hobby extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Value("${url.path}")
    private String address;

    @Column(nullable = false)
    private String img = address + "images/default.jpg";

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "hobby", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @Formula("(SELECT count(1) FROM comment cmt WHERE cmt.hobby_id = id)")
    private int commentsCount;

    public Hobby(UserDetailsImpl userDetails, HobbyRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.url = requestDto.getUrl();
        this.user = userDetails.getUser();
    }

    public void update(HobbyRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.img = requestDto.getImg();
        this.content = requestDto.getContent();
        this.url = requestDto.getUrl();
    }
}