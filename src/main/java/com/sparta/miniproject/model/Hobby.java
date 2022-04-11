package com.sparta.miniproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.miniproject.dto.HobbyRequestDto;
import com.sparta.miniproject.security.UserDetailsImpl;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Getter
//@Data
@NoArgsConstructor
@Entity
public class Hobby extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.EAGER) // hobby을 불러올 때 반드시 User객체를 같이 불러오는 설정 = default 설정
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "hobby", fetch = FetchType.EAGER) // mappedBy 연관관계 주인이 아니다.(FK가 아니다)
    @JsonIgnoreProperties({"hobby"}) // 무한참조 방지 (Comment 객체에 참조된 hobby 객체 연결을 무시한다.)
    private List<Comment> commentList; // 컬럼 추가 없이 값만 불러오기 위해 JoinColumn 사용x

    public Hobby(UserDetailsImpl userDetails, HobbyRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.img = requestDto.getImg();
        this.content = requestDto.getContent();
        this.url = requestDto.getUrl();
        this.user = userDetails.getUser();
    }
}
