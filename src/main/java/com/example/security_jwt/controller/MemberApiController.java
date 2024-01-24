package com.example.security_jwt.controller;


import com.example.security_jwt.dto.MemberSignUpReqDTO;
import com.example.security_jwt.global.mesage.MessageResponse;
import com.example.security_jwt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        Long joinMemberId = memberService.join(request);
        return ResponseEntity.ok(new MessageResponse("회원가입 완료."));
    }

        //로그인

}
