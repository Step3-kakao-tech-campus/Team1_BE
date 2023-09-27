package com.example.team1_be.domain.Worktime;

import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Day.Weekday;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WorktimeRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private WeekRepository weekRepository;
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private WorktimeRepository worktimeRepository;
    @DisplayName("근무일자를 생성할 수 있다.")
    @Test
    void test1() {
        Group group = Group.builder()
                .name("맘스터치")
                .phoneNumber("010-1111-1111")
                .address("부산광역시")
                .build();

        Schedule schedule = Schedule.builder()
                .group(group)
                .build();

        Week week = Week.builder()
                .schedule(schedule)
                .startTime(LocalDateTime.now())
                .build();

        Day day = Day.builder()
                .weekday(Weekday.Monday)
                .week(week)
                .build();

        Worktime.builder()
                .day(day)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .amount(2)
                .build();
    }

    @DisplayName("근무일자를 저장할 수 있다.")
    @Test
    void test2() {
        Group group = Group.builder()
                .name("맘스터치")
                .phoneNumber("010-1111-1111")
                .address("부산광역시")
                .build();
        groupRepository.save(group);

        Schedule schedule = Schedule.builder()
                .group(group)
                .build();
        scheduleRepository.save(schedule);

        Week week = Week.builder()
                .schedule(schedule)
                .startTime(LocalDateTime.now())
                .build();
        weekRepository.save(week);

        Day day = Day.builder()
                .weekday(Weekday.Monday)
                .week(week)
                .build();
        dayRepository.save(day);

        Worktime worktime = Worktime.builder()
                .day(day)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .amount(2)
                .build();
        worktimeRepository.save(worktime);
    }
}