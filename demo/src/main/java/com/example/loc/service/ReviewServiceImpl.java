package com.example.loc.service;

import com.example.loc.domain.Location.Location;
import com.example.loc.domain.Member.Member;
import com.example.loc.domain.review.Review;
import com.example.loc.dto.MyReviewDTO;
import com.example.loc.dto.MyReviewResDTO;
import com.example.loc.dto.SaveReviewReqDTO;
import com.example.loc.repository.LocationRepository;
import com.example.loc.repository.MemberRepository;
import com.example.loc.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;


    @Override
    @Transactional
    public Long saveReview(SaveReviewReqDTO request) {
        Member findMember = memberRepository.findById(request.getMemberId()).orElseThrow(
                () -> new IllegalArgumentException("Member Not Exist")
        );

        Location findLocation = locationRepository.findById(request.getLocationId()).orElseThrow(
                () -> new IllegalArgumentException("Location Not Exist")
        );

        Review review = Review
                .builder()
                .review(request.getReview())
                .grade(request.getGrade())
                .member(findMember)
                .location(findLocation)
                .build();
        Review savedReview = reviewRepository.save(review);

        return savedReview.getId();
    }

    @Override
    @Transactional
    public MyReviewResDTO getMyReview(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member Not Exist")
        );

        List<Review> reviews = reviewRepository.findReviewsByMemberId(findMember.getId());
        List<MyReviewDTO> myReviewDTOList = new ArrayList<>();

        for (Review review : reviews) {
            MyReviewDTO myReviewDto = MyReviewDTO
                    .builder()
                    .review(review.getReview())
                    .grade(review.getGrade())
                    .name(review.getLocation().getName())
                    .type(review.getLocation().getType())
                    .build();
            myReviewDTOList.add(myReviewDto);
        }

        return new MyReviewResDTO(myReviewDTOList, myReviewDTOList.size());
    }
}
