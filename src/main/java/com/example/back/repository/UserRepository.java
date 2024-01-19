package com.example.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUserid(String userid);
}
