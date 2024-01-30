package com.example.loc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loc.domain.Location.LocationImg;

public interface LocationImgRepository extends JpaRepository<LocationImg, Long>{
    
    Optional<LocationImg> findByLocationId(Long locationId);
}
