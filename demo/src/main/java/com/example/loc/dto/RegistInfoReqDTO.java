package com.example.loc.dto;

import com.example.loc.domain.Location.Location;
import com.example.loc.domain.Location.Type;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class RegistInfoReqDTO {
    
    private String name;
    private String comment;
    private String phone;
    private String addr;
    private Type type;

    // Owner id 가져와야함 (Member Table 조인 필수)
    private Long memberId;
}
