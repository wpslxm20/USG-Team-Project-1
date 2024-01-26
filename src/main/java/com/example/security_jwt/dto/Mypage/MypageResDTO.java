package com.example.security_jwt.dto.Mypage;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MypageResDTO {

    private String email;
    private String store_name;
    private String address;
    private String content; //업종
    private String rating;
    private LocalDateTime date;
    private String review;
}
