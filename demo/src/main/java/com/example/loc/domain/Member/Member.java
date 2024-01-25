package com.example.loc.domain.Member;

import java.time.LocalDate;
import java.util.List;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.loc.domain.Location.Location;

import jakarta.persistence.*;

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
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 일대다 관계
    @OneToMany(mappedBy = "member")
    private List<Location> locations;

    @Builder
    public Member(String nickname, String email, String password, LocalDate birth, Role role, Gender gender) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.role = role;
        this.gender = gender;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
