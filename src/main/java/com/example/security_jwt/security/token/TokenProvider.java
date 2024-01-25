package com.example.security_jwt.security.token;

import java.util.Map;

public interface TokenProvider {

    String createAccessToken(String username);
    String createRefreshToken(String username);
    String createJwt(String subject, Long expiration, Map<String, Object> claim);
    Claims get(String jwt) throws JwtException;
    boolean isExpiration(String jwt) throws JwtException;
    void saveRefreshTokenInRedis(String username, String refreshToken);
    boolean canRefresh(String refreshToken) throws JwtException;
}
