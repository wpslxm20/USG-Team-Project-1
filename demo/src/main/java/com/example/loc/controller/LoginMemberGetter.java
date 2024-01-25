package com.example.loc.controller;

import com.example.loc.domain.Member.Member;
import com.example.loc.global.token.TokenParser;
import com.example.loc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginMemberGetter {

    private final TokenParser parser;
    private final MemberRepository memberRepository;

    public Member getLoginMember(String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        String email = (String) parser.getToken(token).get("email");
        Member loginMember = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Member Not Exist")
        );

        return loginMember;
    }
}
