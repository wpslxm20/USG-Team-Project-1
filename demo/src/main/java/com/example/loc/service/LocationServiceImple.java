package com.example.loc.service;

import org.springframework.stereotype.Service;

import com.example.loc.domain.Location.Location;
import com.example.loc.dto.RegistInfoReqDTO;
import com.example.loc.repository.LocationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImple implements LocationService{

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public Long reg(RegistInfoReqDTO regDTO) {
        // 일단, DB에 같은 장소가 존재하면 안되고
        // 두 번째로 이거 하면서 생각난건데, 
        if (locationRepository.findByAddrAndName(regDTO.getAddr(),regDTO.getName()).isPresent()) {
            throw new Error("이미 존재하는 매장입니다.");
        }

        Long owner_id = regDTO.getOwner_id();

        Location location = Location.builder()
            .name(regDTO.getName())
            .phone(regDTO.getPhone())
            .comment(regDTO.getComment())
            .addr(regDTO.getAddr())
            .type(regDTO.getType())
            .build();

        Location savedLoc = locationRepository.save(location);
        return savedLoc.getId();
    }
    
}
