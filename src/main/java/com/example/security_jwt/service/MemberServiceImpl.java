package com.example.security_jwt.service;

import com.example.security_jwt.domain.Role;
import com.example.security_jwt.dto.MemberLoginReqDTO;
import com.example.security_jwt.dto.MemberLoginResDTO;
import com.example.security_jwt.dto.RefreshTokenReq;
import com.example.security_jwt.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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
    public MemberLoginResDTO regenerateToken(RefreshTokenReq refreshTokenReq){
        String refreshToken = refreshTokenReq.getRefreshToken();

        if(jwtTokenProvider.isExpiration(refreshToken)){
            throw new IllegalArgumentException("만료된 refresh Token입니다.");
        }

        String userid = (String) jwtTokenProvider.get(refreshToken).get("userid");
        Role role = (Role) jwtTokenProvider.get(refreshToken).get("role");

        Member findMember = memberRepository.findByEmail(userid).orElseThrow(
                () -> new IllegalArgumentException("회원이 존재하지 않습니다.")
        );

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
}
