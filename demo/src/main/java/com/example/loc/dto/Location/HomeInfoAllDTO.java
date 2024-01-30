package com.example.loc.dto;

import com.example.loc.domain.Location.LocationImg;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HomeInfoAllDTO {
    
    private Long id;
    private String name;
    private String comment;
    private String imgUrl;

}
