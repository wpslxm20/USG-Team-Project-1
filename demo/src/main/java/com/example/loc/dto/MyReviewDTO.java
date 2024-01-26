package com.example.loc.dto;

import com.example.loc.domain.Location.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyReviewDTO {

    private String name;
    private Type type;
    private String review;
    private Double grade;
}
