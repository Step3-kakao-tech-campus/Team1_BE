package com.example.team1_be;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Notification.Notification;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Notification.NotificationType;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Sql("/data.sql")
public class BaseTest {
    protected final UserRepository userRepository;
    protected final GroupRepository groupRepository;
    protected final MemberRepository memberRepository;
    protected final NotificationRepository notificationRepository;
    protected final DayRepository dayRepository;
    protected final ApplyRepository applyRepository;
    protected final WeekRepository weekRepository;
    protected final WorktimeRepository worktimeRepository;
    protected final ScheduleRepository scheduleRepository;
    protected final SubstituteRepository substituteRepository;
    protected final EntityManager em;

    public BaseTest(UserRepository userRepository, GroupRepository groupRepository, MemberRepository memberRepository, NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository, SubstituteRepository substituteRepository, EntityManager em) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.notificationRepository = notificationRepository;
        this.dayRepository = dayRepository;
        this.applyRepository = applyRepository;
        this.weekRepository = weekRepository;
        this.worktimeRepository = worktimeRepository;
        this.scheduleRepository = scheduleRepository;
        this.substituteRepository = substituteRepository;
        this.em = em;
    }
}
