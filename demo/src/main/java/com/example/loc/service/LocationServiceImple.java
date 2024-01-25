package com.example.loc.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.loc.domain.Location.Location;
import com.example.loc.domain.Member.Member;
import com.example.loc.dto.RegistInfoReqDTO;
import com.example.loc.repository.LocationRepository;
import com.example.loc.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImple implements LocationService{

    private final LocationRepository locationRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long reg(RegistInfoReqDTO regDTO) {

        // 일단, DB에 같은 장소와 Name이 존재하면 안되고
        if (locationRepository.findByAddrAndName(regDTO.getAddr(),regDTO.getName()).isPresent()) {
            throw new Error("이미 존재하는 매장입니다.");
        }

        // memberID를 통해서 받아온 데이터의 id가 존재하는지 확인하는 것
        Optional<Member> memberCheck = memberRepository.findById(regDTO.getMemberId());

        if (memberCheck.isEmpty()) {
            // 어짜피 프론트에서 제대로 처리했으면, 이 메시지는 뜨지 않을 것임
            throw new Error("회원정보가 존재하지 않습니다.");    
        }

        Member member = memberCheck.get();

        Location location = Location.builder()
            .name(regDTO.getName())
            .phone(regDTO.getPhone())
            .comment(regDTO.getComment())
            .addr(regDTO.getAddr())
            .type(regDTO.getType())
            .member(member)
            .build();

        Location savedLoc = locationRepository.save(location);
        return savedLoc.getId();
    }
    
}
