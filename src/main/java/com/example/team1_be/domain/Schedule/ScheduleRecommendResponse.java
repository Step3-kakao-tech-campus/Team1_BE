package com.example.team1_be.domain.Schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 스케줄 신청 현황 (스케줄 추천 리스트) Request DTO
 */
public class ScheduleRecommendResponse {

    @Getter
    @Setter
    private class ScheduleRecommend {
        String title;                                           // 시간대명
        List<ScheduleRecommendResponse.Applicant> applicants;   // 신청자 목록
        Integer amount;                                         // 인원수
    }


    @Getter
    @Setter
    private class Applicant {
        Integer memberId;           // 유저 id
        String name;                // 유저 이름
    }


    @Getter
    @Setter
    private class Recommendation {
        Integer id;                             // 추천 스케줄 id
        List<ScheduleRecommend> monday;         // 요일별 스케줄 추천
        List<ScheduleRecommend> tuesday;
        List<ScheduleRecommend> wednesday;
        List<ScheduleRecommend> thursday;
        List<ScheduleRecommend> friday;
        List<ScheduleRecommend> saturday;
        List<ScheduleRecommend> sunday;
    }

    List<Recommendation> recommendations;
}