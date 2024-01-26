package com.example.security_jwt.repository;

import com.example.security_jwt.domain.Member.Member;
import com.example.security_jwt.domain.Mypage.Mypage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MypageRepository extends JpaRepository<Mypage, Long> {
    List<Mypage> findByEmail(String email);
}
