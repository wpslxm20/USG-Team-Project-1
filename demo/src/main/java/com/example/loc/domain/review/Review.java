package com.example.loc.domain.review;

import com.example.loc.domain.Location.Location;
import com.example.loc.domain.Member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    private String review;
    private Double grade;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reg_id")
    private Location location;

    // 사진 필요여부 이후 결정

    @Builder
    public Review(String review, Double grade, Member member, Location location) {
        this.review = review;
        this.grade = grade;
        this.member = member;
        this.location = location;
    }

    public void updateReview(String review, Double grade) {
        this.review = review;
        this.grade = grade;
    }
}
