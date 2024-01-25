package com.example.gateway.security.filter;

import com.example.gateway.domain.member.Member;
import com.example.gateway.repository.MemberRepository;
import com.example.gateway.security.api.ApiList;
import com.example.gateway.security.context.MemberContext;
import com.example.gateway.token.TokenParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenParser parser;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token;
            String email;
            boolean isBlackList = checkBlackList(request.getRequestURI());
            // 헤더가 null 이 아니고 올바른 토큰이라면
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && isBlackList ) {
                // 토큰 추출
                token = authorizationHeader.substring(7);
                // 만료 체크
                if (parser.isExpiration(token)) {
                    throw new IllegalArgumentException("AccessToken Expired");
                }

                // claim 을 받아와 정보 추출
                email = (String) parser.getToken(token).get("email");

                // DB 에 정보가 있는지 확인
                Member findMember = memberRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("Member Not Exist"));

                List<GrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority(findMember.getRole().name()));

                MemberContext memberContext = new MemberContext(findMember, roles);

                // 인증 정보 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(memberContext, null, memberContext.getAuthorities());
                // SecurityContextHolder에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // 반환 데이터 인코딩 처리
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8"); // JSON 응답을 UTF-8로 설정
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(APPLICATION_JSON_VALUE);

            response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
            response.getWriter().flush();
            response.getWriter().close();

            if (response.getStatus() == HttpStatus.OK.value()) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
    }

    private boolean checkBlackList(String requestURI) {
        boolean isBlackListed = false;

        for (String blackList : ApiList.getBlackList()) {
            if (isPatternMatch(requestURI, blackList)) {
                isBlackListed = true;
                break;
            }
        }

        return isBlackListed;
    }

    private boolean isPatternMatch(String url, String pattern) {
        // 패턴이 일치하면 true 반환, **를 정규 표현식으로 처리
        return url.equals(pattern) || url.matches(pattern.replace("**", ".*"))
                || (pattern.endsWith("/**") && url.startsWith(pattern.substring(0, pattern.length() - 3)));
    }
}
