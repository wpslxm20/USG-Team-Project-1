package com.example.security_jwt.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security_jwt.domain.Member;
import com.example.security_jwt.dto.MemberSignUpReqDTO;
import com.example.security_jwt.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long join(MemberSignUpReqDTO reqDTO) {
        if(memberRepository.findByEmail(reqDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = Member.builder()
                .email(reqDTO.getEmail())
                .password(passwordEncoder.encode(reqDTO.getPassword()))
                .nickname(reqDTO.getNickname())
                .birth(reqDTO.getBirth())
                .gender(reqDTO.getGender())
                .build();
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }
}
