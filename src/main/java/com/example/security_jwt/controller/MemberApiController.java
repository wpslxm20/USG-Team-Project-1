package com.example.security_jwt.controller;

import com.example.security_jwt.dto.*;
import com.example.security_jwt.dto.Member.MemberLoginReqDTO;
import com.example.security_jwt.dto.Member.MemberLoginResDTO;
import com.example.security_jwt.dto.Member.MemberSignUpReqDTO;
import com.example.security_jwt.dto.Mypage.MemberReqDTO;
import com.example.security_jwt.dto.Mypage.MemberResDTO;
import com.example.security_jwt.global.mesage.MessageResponse;
import com.example.security_jwt.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("api/member/signup") //회원가입
    @Operation(summary = "회원 가입")
    public ResponseEntity<MessageResponse> signup(@RequestBody @Valid MemberSignUpReqDTO request){
        memberService.join(request);
        return ResponseEntity.ok(new MessageResponse("회원가입 완료."));
    }

    @PostMapping("api/member/login")  //로그인
    @Operation(summary = "회원가입")
    public ResponseEntity<MessageResponse> login(@RequestBody MemberLoginReqDTO loginReqDTO){
        MemberLoginResDTO memberLoginResDTO = memberService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        return ResponseEntity.ok(new MessageResponse(memberLoginResDTO,"로그인 완료됨"));
    }

    @PostMapping("api/member/resissue")   //refresh 토큰 요청
    @Operation(summary = "토큰 재발행")
    public ResponseEntity<MessageResponse> regenerateToken(@RequestBody RefreshTokenReq refreshTokenReq){
        MemberLoginResDTO memberLoginResDTO = memberService.regenerateToken(refreshTokenReq);
        return ResponseEntity.ok(new MessageResponse(memberLoginResDTO, "refresh 토큰으로 access 토큰 재발행."));
    }

    @GetMapping("api/member")       //회원 정보 조회
    @Operation(summary = "회원 정보 조회 *")
    public ResponseEntity<MessageResponse> GetMember(@RequestBody @Valid MemberReqDTO memberReqDTO) {
        MemberResDTO resDTO = memberService.GetMember(memberReqDTO);
        return ResponseEntity.ok(new MessageResponse(resDTO, "회원정보 조회함."));
    }

    @PutMapping("api/member/modify")    //수정
    @Operation(summary = "회원 정보 수정 *")
    public ResponseEntity<MessageResponse> mypage(@RequestBody @Valid MemberReqDTO memberReqDTO) {
        memberService.modifyMember(memberReqDTO);
        return ResponseEntity.ok(new MessageResponse(memberReqDTO,"회원정보가 수정되었습니다."));
    }
}
