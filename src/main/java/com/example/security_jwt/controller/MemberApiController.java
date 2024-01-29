package com.example.security_jwt.controller;

import com.example.security_jwt.domain.Member.Member;
import com.example.security_jwt.dto.*;
import com.example.security_jwt.dto.Member.MemberLoginReqDTO;
import com.example.security_jwt.dto.Member.MemberLoginResDTO;
import com.example.security_jwt.dto.Member.MemberSignUpReqDTO;
import com.example.security_jwt.dto.Mypage.MemberModifyReqDTO;
import com.example.security_jwt.dto.Mypage.MypageReqDTO;
import com.example.security_jwt.dto.Mypage.MypageResDTO;
import com.example.security_jwt.global.mesage.MessageResponse;
import com.example.security_jwt.security.context.MemberContext;
import com.example.security_jwt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("signup") //회원가입
    public ResponseEntity<MessageResponse> signup(@RequestBody @Valid MemberSignUpReqDTO request){
        memberService.join(request);
        return ResponseEntity.ok(new MessageResponse("회원가입 완료."));
    }

    @PostMapping("login")  //로그인
    public ResponseEntity<MessageResponse> login(@RequestBody MemberLoginReqDTO loginReqDTO){
        MemberLoginResDTO memberLoginResDTO = memberService.login(loginReqDTO.getEmail(), loginReqDTO.getPassword());
        return ResponseEntity.ok(new MessageResponse(memberLoginResDTO,"로그인 완료됨"));
    }

    @PostMapping("resissue")   //refresh 토큰 요청
    public ResponseEntity<MessageResponse> regenerateToken(@RequestBody RefreshTokenReq refreshTokenReq){
        MemberLoginResDTO memberLoginResDTO = memberService.regenerateToken(refreshTokenReq);

        return ResponseEntity.ok(new MessageResponse(memberLoginResDTO, "refresh 토큰으로 access 토큰 재발행."));
    }


    @PutMapping("mypage/modify")    //수정
    public ResponseEntity<MessageResponse> mypage(@RequestBody @Valid MemberModifyReqDTO memberModifyReqDTO) {
        memberService.modifyMember(memberModifyReqDTO);
        return ResponseEntity.ok(new MessageResponse(memberModifyReqDTO,"회원정보가 수정되었습니다."));
    }

    @GetMapping("mypage/like")      //관심목록
    public ResponseEntity<MessageResponse> GetLike(@RequestBody @Valid MypageReqDTO reqDTO) {
        List<MypageResDTO> mypageResDTO = memberService.GetLike(reqDTO);
        return ResponseEntity.ok(new MessageResponse(mypageResDTO, "회원 관심 정보 확인"));
    }

    @GetMapping("mypage/review")    //리뷰 확인
    public ResponseEntity<MessageResponse> GetReview(@RequestBody @Valid MypageReqDTO reqDTO) {
        List<MypageResDTO> mypageResDTO = memberService.GetReview(reqDTO);
        return ResponseEntity.ok(new MessageResponse(mypageResDTO, "회원 리뷰 정보 확인"));
    }
}
