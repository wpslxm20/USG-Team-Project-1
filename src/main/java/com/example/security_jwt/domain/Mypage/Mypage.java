package com.example.security_jwt.domain.Mypage;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table (name="mypage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mypage {
    @Id
    @Column(name = "mypage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;   //아이디
    private String Storename;   //업장 이름
    private String address; //주소
    private String type; //업종
    private String rating;  //평점
    private LocalDateTime date; //날짜
    private String review;  //리뷰내용
    private String image;   //이미지
}
