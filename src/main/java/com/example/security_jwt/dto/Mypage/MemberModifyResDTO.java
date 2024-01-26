package com.example.security_jwt.dto.Mypage;

import com.example.security_jwt.domain.Member.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class MemberModifyResDTO {
    private String nickname;
    private String password;
    private LocalDateTime birth;
    private Gender gender;
}
