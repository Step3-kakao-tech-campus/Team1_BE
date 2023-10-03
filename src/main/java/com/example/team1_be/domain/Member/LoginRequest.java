package com.example.team1_be.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 Request DTO
 */
@Getter
@NoArgsConstructor
public class LoginRequest {
    private String code;
}
