package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.hobby.HobbyRequestDto;
import com.sparta.miniproject.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

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

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "hobby", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    // Formula 에 대해서는 추가적인 검증 작업이 필요합니다. 테스트에 관한 것은 팀원들과 협력해 보도록 합시다.
    @Formula("(SELECT count(1) FROM Comment comment WHERE comment.hobby_id = id)")
    private int commentsCount;

    public Hobby(UserDetailsImpl userDetails, HobbyRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.img = requestDto.getImg();
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