package com.example.team1_be.domain.Week;

import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.utils.errors.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeekService {
    private final int LATEST_WEEK_LIMIT = 1;
    private final WeekRepository weekRepository;

    @Transactional
    public Week createWeek(Schedule schedule, LocalDate weekStartDate) {
        Week week = Week.builder()
                .schedule(schedule)
                .status(WeekRecruitmentStatus.STARTED)
                .startDate(weekStartDate)
                .build();
        return weekRepository.save(week);
    }

    @Transactional
    public void updateWeekStatus(Week week, WeekRecruitmentStatus weekRecruitmentStatus) {
        weekRepository.save(week.updateStatus(weekRecruitmentStatus));
    }

    public Week findByScheduleAndStartDate(Schedule schedule, LocalDate startDate) {
        return weekRepository.findByScheduleIdAndStartDate(schedule.getId(), startDate)
                .orElse(null);
    }

    public List<Week> findLatestByScheduleAndStatus(Schedule schedule, WeekRecruitmentStatus status) {
        return weekRepository.findLatestByScheduleAndStatus(schedule.getId(), status, PageRequest.of(0, LATEST_WEEK_LIMIT)).getContent();
    }

    public Week findByScheduleIdStartDateAndStatus(Schedule schedule, LocalDate startDate, WeekRecruitmentStatus status) {
        return weekRepository.findByScheduleIdStartDateAndStatus(schedule.getId(), startDate, status)
                .orElseThrow(() -> new CustomException("모집 중인 스케줄을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }

    public List<Week> findByScheduleAndYearMonthAndStatus(LocalDate date, LocalDate toDate, Schedule schedule, WeekRecruitmentStatus weekRecruitmentStatus) {
        return weekRepository.findByScheduleAndYearMonthAndStatus(date, toDate, schedule.getId(), weekRecruitmentStatus);
    }
}
