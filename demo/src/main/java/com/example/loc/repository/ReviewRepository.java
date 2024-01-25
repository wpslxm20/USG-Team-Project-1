package com.example.loc.repository;

import com.example.loc.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select r from Review r" +
            " join fetch r.member rm" +
            " join fetch r.location rl" +
            " where r.member.id = :memberId")
    List<Review> findReviewsByMemberId(Long memberId);
}
