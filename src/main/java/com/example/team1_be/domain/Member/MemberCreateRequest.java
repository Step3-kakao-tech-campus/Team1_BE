package com.example.team1_be.domain.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 Request DTO
 */
@Getter
@NoArgsConstructor
public class MemberCreateRequest {
    private String userName;      // 유저 이름
    private String isAdmin;       // 관리자 여부
}

