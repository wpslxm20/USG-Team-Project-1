package com.example.security_jwt.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import java.util.Map;

public interface TokenProvider{

    String createAccessToken(String userid);
    String createRefreshToken(String userid);
    String createJwt(String subject, Long expiration, Map<String, Object> claim);
    Claims get(String jwt) throws JwtException;
    boolean isExpiration(String jwt) throws JwtException;
    void saveRefreshTokenInRedis(String userid, String refreshToken);
    boolean canRefresh(String refreshToken) throws JwtException;
}
