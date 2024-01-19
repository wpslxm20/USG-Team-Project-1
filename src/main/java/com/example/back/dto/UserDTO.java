package com.example.back.dto;

import com.example.back.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private String userid;
    private String username;
    private String password;

    //헤더 파일 만들기.
    public User toEntity(){
        return User.builder()
        .userid(this.userid)
        .username(this.username)
        .password(this.password)
        .build();
    }
}
