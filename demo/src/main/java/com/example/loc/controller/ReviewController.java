package com.example.loc.controller;

import com.example.loc.dto.SaveReviewReqDTO;
import com.example.loc.global.message.MessageResponse;
import com.example.loc.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/review")
    public ResponseEntity<MessageResponse> saveReview(@RequestBody SaveReviewReqDTO request) {

        Long savedReviewId = reviewService.saveReview(request);

        return ResponseEntity.ok(new MessageResponse(savedReviewId, "리뷰 작성이 완료되었습니다."));
    }
}
