package com.example.loc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyReviewResDTO {

    private List<MyReviewDTO> myReviewDTOS;
    private Integer myReviewCount;
}
