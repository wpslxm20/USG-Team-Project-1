package com.example.loc.dto.Location;

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
