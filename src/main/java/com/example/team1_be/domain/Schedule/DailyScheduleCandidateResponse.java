package com.example.team1_be.domain.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 특정일 근무 가능 pool 확인 Response DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DailyScheduleCandidateResponse {

    private String title;                   // 시간대명
    private List<Applicant> applicants;     // 근무 가능 인원 목록
    private Integer amount;                 // 근무 가능 인원수


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Applicant {
        private Integer memberId;               // 근무 가능 member id
        private String name;                    // 근무 가능 member 이름
    }
}
