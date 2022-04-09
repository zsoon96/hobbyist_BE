package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.HobbyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Entity
@Getter
@NoArgsConstructor
public class Hobby extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

//    @Column(nullable = false)
//    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String url;

    public Hobby(HobbyRequestDto requestDto){
//        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.img = requestDto.getImg();
        this.content = requestDto.getContent();
        this.url = requestDto.getUrl();
    }

    public void update(HobbyRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.img = requestDto.getImg();
        this.content = requestDto.getContent();
        this.url = requestDto.getUrl();
    }
}
