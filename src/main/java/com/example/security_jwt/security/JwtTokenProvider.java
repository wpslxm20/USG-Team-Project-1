package com.example.security_jwt.security;

import com.example.security_jwt.domain.Member.Role;
import com.example.security_jwt.security.token.TokenProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtTokenProvider implements TokenProvider {
    private final RedisTemplate<String, String> redisTemplate;
    private final Key key;
    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 2L; // 2 hours
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L; // 30 days

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret.key}") String Key, RedisTemplate redisTemplate) {
        byte[] keyBytes = Decoders.BASE64.decode(Key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisTemplate = redisTemplate;
    }

    public String createJwt(String subject, Long expiration, Map<String, Object> claim) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256);
        log.info("exp: " + expiration);
        if (claim != null) {
            jwtBuilder.setClaims(claim);
        }

        // 만료 기한 설정
        if (expiration != null) {
            jwtBuilder.setExpiration(new Date(new Date().getTime() + expiration));
        }

        return jwtBuilder.compact();
    }

    @Override
    public String createAccessToken(String userid, Role role) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("role", role.name()); // 사용자 Role
        claim.put("userid", userid);  //사용자 ID
        return createJwt("ACCESS_TOKEN", ACCESS_TOKEN_EXPIRATION_TIME, claim);
    }
    @Override
    public String createRefreshToken(String userid, Role role){
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("role", role);
        claim.put("userid", userid);
        String refreshToken = createJwt("REFRESH_TOKEN", REFRESH_TOKEN_EXPIRATION_TIME, claim);
        saveRefreshTokenInRedis(userid, refreshToken);
        return refreshToken;
    }

    public Claims get(String jwt) throws JwtException {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    @Override
    public boolean isExpiration(String jwt) throws JwtException {
        log.info("토큰 만료 여부 체크");
        try {
            return get(jwt).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    @Override
    public void saveRefreshTokenInRedis(String userid, String refreshToken) {
        redisTemplate.opsForValue().set(
                userid,
                refreshToken,
                REFRESH_TOKEN_EXPIRATION_TIME,
                TimeUnit.MILLISECONDS
        );
        log.info("redis 에 refresh token 저장");
    }

    @Override
    public boolean canRefresh(String refreshToken) throws JwtException {
        Claims claims = get(refreshToken);
        long expirationTime = claims.getExpiration().getTime();
        long weekTime = 1000 * 60 * 60 * 24 * 7L;

        return new Date().getTime() > (expirationTime - weekTime);
    }
}
