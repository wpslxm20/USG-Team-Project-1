package com.example.security_jwt.dto.Mypage;

import com.example.security_jwt.domain.Member.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MemberReqDTO {
    private String email;
    private String nickname;
    private LocalDateTime birth;
    private Gender gender;
    private MultipartFile image; //!!!!!!!!!이미지 업로드 관련 부분
}
