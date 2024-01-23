package com.example.security_jwt.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security_jwt.domain.Member;
import com.example.security_jwt.dto.MemberSignUpReqDTO;
import com.example.security_jwt.repository.MemberRepository;

public class MemberServiceImpl implements MemberService{
        private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Long join(MemberSignUpReqDTO reqDTO) {
        if(memberRepository.findByEmail(reqDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = memberRepository.save(reqDTO.toEntity());
        member.passwordEncode(passwordEncoder);
        member.addUserAuthority();
        return member.getId();
    }
}
