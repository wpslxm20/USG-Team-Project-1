package com.example.security_jwt.service;

import com.example.security_jwt.domain.Role;
import com.example.security_jwt.dto.MemberLoginReqDTO;
import com.example.security_jwt.dto.MemberLoginResDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security_jwt.domain.Member;
import com.example.security_jwt.dto.MemberSignUpReqDTO;
import com.example.security_jwt.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long join(MemberSignUpReqDTO reqDTO) {
        if(memberRepository.findByEmail(reqDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Boolean isCustomer = reqDTO.getIsCustomer();
        Member member;

        if (isCustomer) {
            member = Member.builder()
                    .email(reqDTO.getEmail())
                    .password(passwordEncoder.encode(reqDTO.getPassword()))
                    .nickname(reqDTO.getNickname())
                    .birth(reqDTO.getBirth())
                    .gender(reqDTO.getGender())
                    .role(Role.Customer)
                    .build();
        } else {
            member = Member.builder()
                    .email(reqDTO.getEmail())
                    .password(passwordEncoder.encode(reqDTO.getPassword()))
                    .nickname(reqDTO.getNickname())
                    .birth(reqDTO.getBirth())
                    .gender(reqDTO.getGender())
                    .role(Role.Owner)
                    .build();
        }

        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }


    @Override
    public MemberLoginResDTO login(String email, String password){
        //Id 검증
        Member findMember = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("이미 가입된 이메일 입니다.")
        );

        if(!passwordEncoder.matches(password, findMember.getPassword())){
            throw new IllegalArgumentException("비밀번호가 잘못되었습니다.");
        }
        return MemberLoginResDTO.builder()
                .role(findMember.getRole())
                .build();
    }
}
