package com.example.team1_be.domain.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 스케줄 신청 현황 (스케줄 추천 리스트) Request DTO
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRecommendResponse {


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ScheduleRecommend {
        private String title;                                           // 시간대명
        private List<ScheduleRecommendResponse.Applicant> applicants;   // 신청자 목록
        private Integer amount;                                         // 인원수
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Applicant {
        private Integer memberId;           // 유저 id
        private String name;                // 유저 이름
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Recommendation {
        private Integer id;                             // 추천 스케줄 id
        private List<ScheduleRecommend> monday;         // 요일별 스케줄 추천
        private List<ScheduleRecommend> tuesday;
        private List<ScheduleRecommend> wednesday;
        private List<ScheduleRecommend> thursday;
        private List<ScheduleRecommend> friday;
        private List<ScheduleRecommend> saturday;
        private List<ScheduleRecommend> sunday;
    }

    private List<Recommendation> recommendations;
}