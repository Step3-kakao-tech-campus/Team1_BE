package com.example.team1_be.domain.Member;

import lombok.Data;

/**
 * 회원가입 Request DTO
 */
@Data
public class MemberCreateRequest {
    String userName;      // 유저 이름
    String isAdmin;       // 관리자 여부
}

