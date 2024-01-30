package com.example.security_jwt.global.security.token;

import com.example.security_jwt.domain.Member.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import java.util.Map;

public interface TokenProvider{

    String createAccessToken(String userid, Role role);
    String createRefreshToken(String userid, Role role);
    String createJwt(String subject, Long expiration, Map<String, Object> claim);
    Claims get(String jwt) throws JwtException;
    boolean isExpiration(String jwt) throws JwtException;
    void saveRefreshTokenInRedis(String userid, String refreshToken);
    boolean canRefresh(String refreshToken) throws JwtException;
}
