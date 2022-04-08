package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    public User(SignupRequestDto requestDto){
        this.loginId = requestDto.getLoginId();
        this.nickName = requestDto.getNickName();
        this.password = requestDto.getPassword();
    }

}
