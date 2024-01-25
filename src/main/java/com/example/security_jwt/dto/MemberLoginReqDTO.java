package com.example.security_jwt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLoginReqDTO {
    private String email;
    private String password;
}