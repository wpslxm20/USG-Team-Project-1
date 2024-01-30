package com.example.loc.dto.Location;

import jakarta.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.example.loc.domain.Location.Location;
import com.example.loc.domain.Location.Type;
import com.example.loc.domain.Member.Member;

import lombok.*;

@Getter
@Builder
public class RegistInfoReqDTO {
    
    private Long id; // Location id

    @NotNull(message = "이름은 필수 입력 값")
    private String name;
    @NotNull(message = "코멘트는 필수 입력 값")
    private String comment;
    @NotNull(message = "전화번호는 필수 입력 값")
    private String phone;
    @NotNull(message = "주소는 필수 입력 값")
    private String addr;
    @NotNull(message = "타입은 필수 입력 값")
    private Type type;

    private RegistImgReqDTO registImgReqDTO; // 등록된 파일
    private Long registImgId; // 이미지 ID

    // Owner id 가져와야함 (Member Table 조인 필수)
    private Long memberId;

    private static ModelMapper modelMapper = new ModelMapper();

    public Location createLocation(Member member) {
        Location location = modelMapper.map(this, Location.class);
        location.setName(name);
        location.setComment(comment);
        location.setPhone(phone);
        location.setAddr(addr);
        location.setType(type);
        location.setMember(member);
        return location;
    }

    public static RegistInfoReqDTO of(Location location) {
        return modelMapper.map(location, RegistInfoReqDTO.class);
    }
}
