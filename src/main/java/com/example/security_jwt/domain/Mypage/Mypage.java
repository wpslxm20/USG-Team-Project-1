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

    private String email;
    private String Storename;
    private String address;
    private String content; //업종
    private String rating;
    private LocalDateTime date;
    private String review;
}
