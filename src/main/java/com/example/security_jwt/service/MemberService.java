package com.example.security_jwt.service;

import com.example.security_jwt.dto.*;
import com.example.security_jwt.dto.Member.MemberLoginResDTO;
import com.example.security_jwt.dto.Member.MemberSignUpReqDTO;
import com.example.security_jwt.dto.Mypage.MemberReqDTO;
import com.example.security_jwt.dto.Mypage.MemberResDTO;

public interface MemberService {        //REQ를 Join하기 위한 서비스 리포지토리
    Long join(MemberSignUpReqDTO reqDTO);

    MemberLoginResDTO regenerateToken(RefreshTokenReq refreshTokenReq);
    MemberLoginResDTO login(String email, String password);
    void modifyMember(MemberReqDTO memberReqDTO);
    MemberResDTO GetMember(MemberReqDTO memberReqDTO);
}
