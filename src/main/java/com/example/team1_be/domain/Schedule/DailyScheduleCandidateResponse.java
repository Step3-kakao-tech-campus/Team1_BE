package com.example.team1_be.domain.Schedule;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 특정일 근무 가능 pool 확인 Response DTO
 */
@Data
public class DailyScheduleCandidateResponse {

    String title;                   // 시간대명
    List<Applicant> applicants;     // 근무 가능 인원 목록
    Integer amount;                 // 근무 가능 인원수


    @Getter
    @Setter
    private class Applicant {
        Integer memberId;               // 근무 가능 member id
        String name;                    // 근무 가능 member 이름
    }
}
