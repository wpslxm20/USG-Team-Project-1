package com.example.loc.service;

import com.example.loc.dto.HomeInfoReqDTO;
import com.example.loc.dto.HomeInfoResDTO;
import com.example.loc.dto.RegistInfoReqDTO;

public interface LocationService {

    // 홈페이지 전송 (프론트에서 id 데이터를 보내면, 그에 맞는 정보 출력)
    Long home(HomeInfoReqDTO reqDTO, HomeInfoResDTO resDTO);

    // 등록
    Long reg(RegistInfoReqDTO regDTO);

    // 삭제

    // 수정

    // 조회
} 
