package com.example.loc.repository;

import com.example.loc.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    // Id(Unique) -> 하나의 Member만 반환
    Optional<Member> findById(Long id);
}
