package com.example.security_jwt.dto.Member;

import java.time.LocalDateTime;

import com.example.security_jwt.domain.Member.Gender;
import com.example.security_jwt.domain.Member.Member;

import lombok.Data;

@Data
public class MemberSignUpResDTO {

    private Long id;
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime birth;
    private Gender gender;

    public MemberSignUpResDTO(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.birth = member.getBirth();
        this.gender = member.getGender();
    }

}
