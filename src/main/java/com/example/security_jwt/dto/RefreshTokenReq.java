package com.example.security_jwt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RefreshTokenReq {
    private String refreshToken;
}
