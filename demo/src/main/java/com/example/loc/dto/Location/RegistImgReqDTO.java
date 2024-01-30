package com.example.loc.dto.Location;

import org.modelmapper.ModelMapper;

import com.example.loc.domain.Location.LocationImg;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class RegistImgReqDTO {

    private Long id;
    private String imgName;
    private String oriImgName;
    private String imageUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static RegistImgReqDTO of(LocationImg locationImg) {
        return modelMapper.map(locationImg, RegistImgReqDTO.class);
    }
}
