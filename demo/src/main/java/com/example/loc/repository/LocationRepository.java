package com.example.loc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loc.domain.Location.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

    // DB에 있는 데이터중 주소와 name이 같은 것을 찾기
    Optional<Location> findByAddrAndName(String addr, String name);
}
