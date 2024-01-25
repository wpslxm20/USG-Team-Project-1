package com.example.security_jwt.controller;

import com.example.security_jwt.dto.MemberLoginReqDTO;
import com.example.security_jwt.dto.MemberLoginResDTO;
import com.example.security_jwt.dto.MemberSignUpReqDTO;
import com.example.security_jwt.dto.RefreshTokenReq;
import com.example.security_jwt.global.mesage.MessageResponse;
import com.example.security_jwt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/signup") //회원가입
    public ResponseEntity<MessageResponse> signup(@RequestBody @Valid MemberSignUpReqDTO request){
        memberService.join(request);
        return ResponseEntity.ok(new MessageResponse("회원가입 완료."));
    }

    @PostMapping("/login")  //로그인
    public ResponseEntity<MessageResponse> login(@RequestBody MemberLoginReqDTO loginReqDTO){
        MemberLoginResDTO memberLoginResDTO = memberService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        return ResponseEntity.ok(new MessageResponse(memberLoginResDTO,"로그인 완료됨"));
    }

    @PostMapping("/resissue")   //refresh 토큰 요청
    public ResponseEntity<MessageResponse> regenerateToken(@RequestBody RefreshTokenReq refreshTokenReq){
        MemberLoginResDTO memberLoginResDTO = memberService.regenerateToken(refreshTokenReq);

        return ResponseEntity.ok(new MessageResponse(memberLoginResDTO, "refresh 토큰으로 access 토큰 재발행."));
    }
}
