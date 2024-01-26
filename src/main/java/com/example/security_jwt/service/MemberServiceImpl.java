package com.example.security_jwt.service;

import com.example.security_jwt.domain.Member.Role;
import com.example.security_jwt.domain.Mypage.Mypage;
import com.example.security_jwt.dto.*;
import com.example.security_jwt.dto.Member.MemberLoginResDTO;
import com.example.security_jwt.dto.Member.MemberSignUpReqDTO;
import com.example.security_jwt.dto.Mypage.MemberModifyReqDTO;
import com.example.security_jwt.dto.Mypage.MypageReqDTO;
import com.example.security_jwt.dto.Mypage.MypageResDTO;
import com.example.security_jwt.repository.MypageRepository;
import com.example.security_jwt.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security_jwt.domain.Member.Member;
import com.example.security_jwt.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MypageRepository mypageRepository;
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

    @Override
    public void modifyMember(MemberModifyReqDTO memberModifyReqDTO){
        Member findMember = memberRepository.findByEmail(memberModifyReqDTO.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("아이디를 찾을 수 없습니다.")
        );

        if (memberModifyReqDTO.getNickname() != null) {
            findMember.ModifyNickName(memberModifyReqDTO.getNickname());
        }
        if (memberModifyReqDTO.getPassword() != null) {
            findMember.ModifyPassword(passwordEncoder.encode(memberModifyReqDTO.getPassword()));
        }
        if (memberModifyReqDTO.getGender() != null){
            findMember.ModifyGender(memberModifyReqDTO.getGender());
        }
        if (memberModifyReqDTO.getBirth() != null){
            findMember.ModifyBirth(memberModifyReqDTO.getBirth());
        }
        // 기타 필드들에 대한 업데이트 로직 추가...

        memberRepository.save(findMember);
    }
    @Override
    public List<MypageResDTO> GetReview(MypageReqDTO reqDTO){
        List<Mypage> mypages = mypageRepository.findByEmail(reqDTO.getEmail());

        if (mypages.isEmpty()) {
            throw new IllegalArgumentException("리뷰 정보를 찾을 수 없습니다.");
        }

        List<MypageResDTO> resDTOs = new ArrayList<>();
        for (Mypage mypage : mypages) {
            MypageResDTO resDTO = MypageResDTO.builder()
                    .email(mypage.getEmail())
                    .store_name(mypage.getStorename())
                    .address(mypage.getAddress())
                    .content(mypage.getContent())
                    .rating(mypage.getRating())
                    .date(mypage.getDate())
                    .review(mypage.getReview())
                    .build();
            resDTOs.add(resDTO);
        }

        return resDTOs;
    }


    @Override
    public List<MypageResDTO> GetLike(MypageReqDTO reqDTO){
        List<Mypage> mypages = mypageRepository.findByEmail(reqDTO.getEmail());

        if (mypages.isEmpty()) {
            throw new IllegalArgumentException("리뷰 정보를 찾을 수 없습니다.");
        }
        List<MypageResDTO> resDTOs = new ArrayList<>();
        for (Mypage mypage : mypages) {
            MypageResDTO resDTO = MypageResDTO.builder()
                    .store_name(mypage.getStorename())
                    .address(mypage.getAddress())
                    .rating(mypage.getRating())
                    .date(mypage.getDate())
                    .content(mypage.getContent())
                    .build();
        }
        return resDTOs;
    }
}
