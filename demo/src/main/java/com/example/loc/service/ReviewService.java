package com.example.loc.service;

import com.example.loc.dto.MyReviewResDTO;
import com.example.loc.dto.SaveReviewReqDTO;

public interface ReviewService {

    Long saveReview(SaveReviewReqDTO request);
    MyReviewResDTO getMyReview(Long memberId);
}
