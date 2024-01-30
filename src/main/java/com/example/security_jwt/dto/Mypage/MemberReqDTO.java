package com.example.security_jwt.dto.Mypage;

import com.example.security_jwt.domain.Member.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MemberReqDTO {
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime birth;
    private Gender gender;

}
