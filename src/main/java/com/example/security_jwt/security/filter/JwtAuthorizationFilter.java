package com.example.security_jwt.security.filter;

import com.example.security_jwt.domain.Member;
import com.example.security_jwt.repository.MemberRepository;
import com.example.security_jwt.security.context.MemberContext;
import com.example.security_jwt.security.token.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String token;
        String nickname;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            token = authorizationHeader.replace("Bearer ", "");

            if (tokenProvider.isExpiration(token)) {
                throw new IllegalArgumentException("Access Token Expired");
            }

            nickname = (String) tokenProvider.get(token).get("nickname");

            Member findMember = memberRepository.findByNickname(nickname).orElseThrow(
                    () -> new IllegalArgumentException("Member Not Found")
            );

            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(findMember.getRole().name()));

            MemberContext memberContext = new MemberContext(findMember, roles);
            Authentication authentication = new UsernamePasswordAuthenticationToken(memberContext, null, memberContext.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }


}
