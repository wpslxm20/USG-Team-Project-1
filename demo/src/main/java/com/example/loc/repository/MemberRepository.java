package com.example.loc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loc.domain.Member.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
    
    // Id(Unique) -> 하나의 Member만 반환
    Optional<Member> findById(Long id);
}
