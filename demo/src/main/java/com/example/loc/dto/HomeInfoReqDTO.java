package com.example.loc.dto;



import java.util.Collections;
import java.util.List;

import com.example.loc.domain.Location.Location;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class HomeInfoReqDTO {
    
    private Long id;
    private String name;
    private String comment;

    // 일단 평점을 받아오는 것이 필요한데
    //private Long meanScore = 4L; // 이건 다른 서비스(Review)에서 가져와야 함
    //private String imageUrl = "../img/dog.jpg"; // 이미지 경로
}
