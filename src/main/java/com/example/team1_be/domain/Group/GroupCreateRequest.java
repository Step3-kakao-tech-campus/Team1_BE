package com.example.team1_be.domain.Group;

import lombok.Data;

/**
 * 그룹 생성하기 Request DTO
 */
@Data
public class GroupCreateRequest {
    String marketName;      // 사업자명
    String marketNumber;    // 사업자번호
    String mainAddress;     // 주소
    String detailAddress;   // 상세주소
}

