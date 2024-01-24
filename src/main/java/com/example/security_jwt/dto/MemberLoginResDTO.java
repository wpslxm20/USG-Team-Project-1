package com.example.security_jwt.dto;

import com.example.security_jwt.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MemberLoginResDTO {
    private String login;
    private String password;
    private Role role;
}