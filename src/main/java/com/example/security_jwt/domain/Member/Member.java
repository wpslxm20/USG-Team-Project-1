package com.example.security_jwt.domain.Member;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table (name="member3")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nickname", nullable = false, unique =true)
    private String nickname;

    @Column(name="email", nullable = false, unique =true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="birth", nullable = false)
    private LocalDateTime birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name="image")
    private String image; // Google Cloud Storage에 저장된 이미지 파일 이름

    @Builder
    public Member(String nickname, String email, String password, LocalDateTime birth, Role role, Gender gender) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.role = role;
        this.gender = gender;
    }
    public void ModifyNickName(String nickname) { this.nickname = nickname; }
    public void ModifyBirth(LocalDateTime birth) { this.birth = birth; }
    public void ModifyGender(Gender gender) { this.gender = gender; }
}
