package com.example.security_jwt.dto.Member;


import java.time.LocalDateTime;

import com.example.security_jwt.domain.Member.Gender;
import com.example.security_jwt.domain.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberSignUpReqDTO {

    private String email;
    private String nickname;
    private String password;
    private LocalDateTime birth;
    private Gender gender;
    private Boolean isCustomer;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .password(password)
                .build();
    }
}
