package com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule;

import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.errors.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendedWeeklyScheduleService {
    private final RecommendedWeeklyScheduleRepository recommendedWeeklyScheduleRepository;

    @Transactional
    public RecommendedWeeklySchedule creatRecommendedWeeklySchedule(RecommendedWeeklySchedule recommendedWeeklySchedule) {
        return recommendedWeeklyScheduleRepository.save(recommendedWeeklySchedule);
    }

    public List<RecommendedWeeklySchedule> findByUser(User user) {
        List<RecommendedWeeklySchedule> recommendedWeeklySchedules = recommendedWeeklyScheduleRepository.findByUser(user.getId());
        if (recommendedWeeklySchedules.isEmpty()) {
            throw new NotFoundException("등록된 추천 스케줄이 존재하지 않습니다.");
        }
        return recommendedWeeklySchedules;
    }

    @Transactional
    public void deleteAll(List<RecommendedWeeklySchedule> recommendedWeeklySchedules) {
        recommendedWeeklyScheduleRepository.deleteAll(recommendedWeeklySchedules);
    }
}
