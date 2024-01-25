package com.example.gateway.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenParser {

    private final Key key;

    @Autowired
    public TokenParser(@Value("${jwt.token.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 복호화
    public Claims getToken(String jwt) throws JwtException {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    // 토큰 만료여부 체크
    public boolean isExpiration(String jwt) throws JwtException {
        try {
            return getToken(jwt).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}
