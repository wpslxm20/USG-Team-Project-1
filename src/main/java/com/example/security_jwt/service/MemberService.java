package com.example.security_jwt.service;

import com.example.security_jwt.dto.MemberLoginResDTO;
import com.example.security_jwt.dto.MemberSignUpReqDTO;
import com.example.security_jwt.dto.RefreshTokenReq;

public interface MemberService {        //REQ를 Join하기 위한 서비스 리포지토리
    Long join(MemberSignUpReqDTO reqDTO);

    MemberLoginResDTO regenerateToken(RefreshTokenReq refreshTokenReq);
    MemberLoginResDTO login(String email, String password);
}
