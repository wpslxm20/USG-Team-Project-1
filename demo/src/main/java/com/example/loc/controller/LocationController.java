package com.example.loc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loc.dto.HomeInfoReqDTO;
import com.example.loc.dto.RegistInfoReqDTO;
import com.example.loc.global.message.MessageResponse;
import com.example.loc.repository.LocationRepository;
import com.example.loc.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Slf4j // log 사용할 수 있게 해주는 거
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;
    private final LocationRepository locationRepository;

    @PostMapping("/home") // 홈페이지에 필요한 데이터 전송
    public ResponseEntity<MessageResponse> sendHome() {
        List<HomeInfoReqDTO> locations = locationRepository.findAllByIdNotNull();

        MessageResponse<List<HomeInfoReqDTO>> response = new MessageResponse<>("홈으로 전송완료");
        response.setData(locations);
        return ResponseEntity.ok(response);
        
    }

    @PostMapping("/reg") // 매장 등록
    public ResponseEntity<MessageResponse> reg(@RequestBody @Valid RegistInfoReqDTO request) {
        Long regId = locationService.reg(request);
        return ResponseEntity.ok(new MessageResponse("매장 등록 완료"));
    }
}
