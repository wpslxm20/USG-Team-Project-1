package com.example.security_jwt.service;

import com.example.security_jwt.domain.Member.Role;
import com.example.security_jwt.dto.*;
import com.example.security_jwt.dto.Member.MemberLoginResDTO;
import com.example.security_jwt.dto.Member.MemberSignUpReqDTO;
import com.example.security_jwt.dto.Mypage.MemberReqDTO;
import com.example.security_jwt.dto.Mypage.MemberResDTO;
import com.example.security_jwt.global.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security_jwt.domain.Member.Member;
import com.example.security_jwt.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

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
    @Transactional
    public MemberLoginResDTO regenerateToken(RefreshTokenReq refreshTokenReq){
        String refreshToken = refreshTokenReq.getRefreshToken();

        if(jwtTokenProvider.isExpiration(refreshToken)){
            throw new IllegalArgumentException("만료된 refresh Token입니다.");
        }

        String userid = (String) jwtTokenProvider.get(refreshToken).get("userid");
        Role role = (Role) jwtTokenProvider.get(refreshToken).get("role");

        String findRefreshToken = redisTemplate.opsForValue().get(userid);
        if(!refreshToken.equals(findRefreshToken)){
            throw new IllegalArgumentException("토큰이 맞지 않음.");
        }

        String newRefreshToken = jwtTokenProvider.createRefreshToken(userid,role);

        MemberLoginResDTO memberLoginResDTO = MemberLoginResDTO.builder()
                .refreshToken(newRefreshToken)
                .accessToken(jwtTokenProvider.createAccessToken(userid,role))
                .build();

        return memberLoginResDTO;
    }

    @Override
    @Transactional
    public MemberLoginResDTO login(String email, String password){
        //Id 검증
        Member findMember = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("아이디를 찾을 수 업습니다.")
        );

        if(!passwordEncoder.matches(password, findMember.getPassword())){
            throw new IllegalArgumentException("비밀번호가 잘못되었습니다.");
        }

        String acessToken = jwtTokenProvider.createAccessToken(findMember.getEmail(), findMember.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(findMember.getEmail(), findMember.getRole());

        return MemberLoginResDTO.builder()
                .accessToken(acessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void modifyMember(MemberReqDTO memberReqDTO){
        Member findMember = memberRepository.findByEmail(memberReqDTO.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("아이디를 찾을 수 없습니다.")
        );

        if (memberReqDTO.getNickname() != null) {
            findMember.ModifyNickName(memberReqDTO.getNickname());
        }
        if (memberReqDTO.getGender() != null){
            findMember.ModifyGender(memberReqDTO.getGender());
        }
        if (memberReqDTO.getBirth() != null){
            findMember.ModifyBirth(memberReqDTO.getBirth());
        }

        memberRepository.save(findMember);
    }
    @Override
    @Transactional
    public MemberResDTO GetMember(MemberReqDTO memberReqDTO){
        Member findMember = memberRepository.findByEmail(memberReqDTO.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("아이디를 찾을 수 없습니다.")
        );
        return MemberResDTO.builder()
                .nickname(findMember.getNickname())
                .birth(findMember.getBirth())
                .gender(findMember.getGender())
                .build();
    }
}
