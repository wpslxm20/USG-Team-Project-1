package com.example.loc.dto;

import com.example.loc.domain.Location.Location;

public class HomeInfoResDTO {
    
    private Long id; // 이게 필요한진 모르겠지만 일단 놔두기
    private String name;
    private String comment;

    // 일단 평점을 받아오는 것이 필요한데
    private Long meanScore; // 이건 다른 서비스(Review)에서 가져와야 함

    public HomeInfoResDTO(Location loc) {
        this.id = loc.getId();
        this.name = loc.getName();
        this.comment = loc.getComment();

        this.meanScore = 4L; // 일단 Review는 생각않고
    }
}
