package com.example.loc.dto;

import com.example.loc.domain.Location.LocationImg;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class HomeInfoDTO {
    
    private Long id;
    private String name;
    private String comment;
    private LocationImg locationImg;
}
