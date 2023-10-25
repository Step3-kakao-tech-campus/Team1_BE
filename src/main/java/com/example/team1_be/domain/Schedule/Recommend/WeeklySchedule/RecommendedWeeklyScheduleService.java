package com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule;

import com.example.team1_be.domain.User.User;
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
        return recommendedWeeklyScheduleRepository.findByUser(user.getId());
    }

    @Transactional
    public void deleteAll(List<RecommendedWeeklySchedule> recommendedWeeklySchedules) {
        recommendedWeeklyScheduleRepository.deleteAll(recommendedWeeklySchedules);
    }
}
