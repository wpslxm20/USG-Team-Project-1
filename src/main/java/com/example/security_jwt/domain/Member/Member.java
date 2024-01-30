package com.example.security_jwt.domain.Member;

import java.time.LocalDateTime;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Getter
@Table (name="member")
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
