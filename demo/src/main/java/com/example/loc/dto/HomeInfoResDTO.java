package com.example.loc.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class HomeInfoResDTO {
    
    // 받아올 데이터는 페이징 처리를 해주기 위해 생각을 해보자
    // 아마도 첫 시작 인덱스 하나만 올 거 같은데
    // 근데 4개로 나눠야 하니까. 그것도 생각해봐야 하네..
    private Long id;
}
