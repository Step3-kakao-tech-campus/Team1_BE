package com.example.team1_be;

import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Day.Weekday;
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
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
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


    @AfterEach
    public void resetRepository() {
        em.clear();

        em.createNativeQuery(
                        "ALTER TABLE substitute ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                        "ALTER TABLE apply ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                        "ALTER TABLE worktime ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                        "ALTER TABLE days ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                        "ALTER TABLE week ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                        "ALTER TABLE schedule ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                        "ALTER TABLE notification ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                        "ALTER TABLE member ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                        "ALTER TABLE groups ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE users ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();

        substituteRepository.deleteAll();
        applyRepository.deleteAll();
        worktimeRepository.deleteAll();
        dayRepository.deleteAll();
        weekRepository.deleteAll();
        scheduleRepository.deleteAll();
        groupRepository.deleteAll();
        memberRepository.deleteAll();
        notificationRepository.deleteAll();
        userRepository.deleteAll();

        em.clear();
    }

    @BeforeEach
    public void initializeRepository() {
        List<User> users = new ArrayList<>();
        User admin = User.builder()
                .kakaoId(1L)
                .name("dlwogns")
                .phoneNumber("010-1111-1111")
                .build();
        users.add(admin);
        User user1 = User.builder()
                .kakaoId(2L)
                .name("dksgkswn")
                .phoneNumber("010-2222-2222")
                .build();
        users.add(user1);
        User user2 = User.builder()
                .kakaoId(3L)
                .name("chldmswls")
                .phoneNumber("010-3333-3333")
                .build();
        users.add(user2);
        User user3 = User.builder()
                .kakaoId(4L)
                .name("dlguswl")
                .phoneNumber("010-4444-4444")
                .build();
        users.add(user3);
        User user4 = User.builder()
                .kakaoId(5L)
                .name("ckwldnjs")
                .phoneNumber("010-5555-5555")
                .build();
        users.add(user4);
        User user5 = User.builder()
                .kakaoId(6L)
                .name("alsgkfls")
                .phoneNumber("010-6666-6666")
                .build();
        users.add(user5);
        userRepository.saveAll(users);

        Group group = Group.builder()
                .address("부산광역시")
                .name("맘스터치")
                .phoneNumber("010-8888-8888")
                .build();

        group = groupRepository.save(group);

        List<Member> members = new ArrayList<>();
        Member member = Member.builder()
                .user(admin)
                .group(group)
                .isAdmin(true)
                .build();
        members.add(member);
        Member member1 = Member.builder()
                .user(user1)
                .group(group)
                .isAdmin(false)
                .build();
        members.add(member1);
        Member member2 = Member.builder()
                .user(user2)
                .group(group)
                .isAdmin(false)
                .build();
        members.add(member2);
        Member member3 = Member.builder()
                .user(user3)
                .group(group)
                .isAdmin(false)
                .build();
        members.add(member3);
        Member member4 = Member.builder()
                .user(user4)
                .group(group)
                .isAdmin(false)
                .build();
        members.add(member4);
        Member member5 = Member.builder()
                .user(user5)
                .group(group)
                .isAdmin(false)
                .build();
        members.add(member5);
        memberRepository.saveAll(members);

        Schedule schedule = Schedule.builder()
                .group(group)
                .build();
        scheduleRepository.save(schedule);

        List<Notification> notifications = new ArrayList<>();
        Notification startNoti = Notification.builder()
                .user(user2)
                .type(NotificationType.START)
                .content("시작 알림")
                .isRead(false)
                .build();
        notifications.add(startNoti);

        Notification endNoti = Notification.builder()
                .user(user2)
                .type(NotificationType.END)
                .content("종료 알림")
                .isRead(false)
                .build();
        notifications.add(endNoti);

        Notification etcNoti = Notification.builder()
                .user(user2)
                .type(NotificationType.ETC)
                .content("기타 알림")
                .isRead(false)
                .build();
        notifications.add(etcNoti);
        notificationRepository.saveAll(notifications);
        
        Week week = Week.builder()
                .schedule(schedule)
                .startTime(LocalDateTime.now())
                .build();
        weekRepository.save(week);

        List<Day> days = new ArrayList<>();
        Day monday = Day.builder()
                .dayOfWeek(7)
                .week(week)
                .build();
        days.add(monday);
        Day tuesday = Day.builder()
                .dayOfWeek(1)
                .week(week)
                .build();
        days.add(tuesday);
        Day wednesday = Day.builder()
                .dayOfWeek(2)
                .week(week)
                .build();
        days.add(wednesday);
        Day thursday = Day.builder()
                .dayOfWeek(3)
                .week(week)
                .build();
        days.add(thursday);
        Day friday = Day.builder()
                .dayOfWeek(4)
                .week(week)
                .build();
        days.add(friday);
        Day saturday = Day.builder()
                .dayOfWeek(5)
                .week(week)
                .build();
        days.add(saturday);
        Day sunday = Day.builder()
                .dayOfWeek(6)
                .week(week)
                .build();
        days.add(sunday);
        dayRepository.saveAll(days);

        List<Worktime> worktimes = new ArrayList<>();
        Worktime worktime1 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-09T00:00:00"))
                .endTime(LocalDateTime.parse("2023-10-09T06:00:00"))
                .day(monday)
                .amount(3)
                .build();
        worktimes.add(worktime1);
        Worktime worktime2 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-09T06:00:00"))
                .endTime(LocalDateTime.parse("2023-10-09T09:00:00"))
                .day(monday)
                .amount(2)
                .build();
        worktimes.add(worktime2);
        Worktime worktime3 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-09T09:00:00"))
                .endTime(LocalDateTime.parse("2023-10-09T15:00:00"))
                .day(monday)
                .amount(1)
                .build();
        worktimes.add(worktime3);

        Worktime worktime4 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-10T00:00:00"))
                .endTime(LocalDateTime.parse("2023-10-10T06:00:00"))
                .day(tuesday)
                .amount(2)
                .build();
        worktimes.add(worktime4);
        Worktime worktime5 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-10T06:00:00"))
                .endTime(LocalDateTime.parse("2023-10-10T09:00:00"))
                .day(tuesday)
                .amount(2)
                .build();
        worktimes.add(worktime5);
        Worktime worktime6 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-10T09:00:00"))
                .endTime(LocalDateTime.parse("2023-10-10T15:00:00"))
                .day(tuesday)
                .amount(2)
                .build();
        worktimes.add(worktime6);

        Worktime worktime7 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-11T00:00:00"))
                .endTime(LocalDateTime.parse("2023-10-11T06:00:00"))
                .day(wednesday)
                .amount(3)
                .build();
        worktimes.add(worktime7);
        Worktime worktime8 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-11T06:00:00"))
                .endTime(LocalDateTime.parse("2023-10-11T09:00:00"))
                .day(wednesday)
                .amount(3)
                .build();
        worktimes.add(worktime8);

        Worktime worktime9 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-12T00:00:00"))
                .endTime(LocalDateTime.parse("2023-10-12T06:00:00"))
                .day(thursday)
                .amount(1)
                .build();
        worktimes.add(worktime9);
        Worktime worktime10 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-12T06:00:00"))
                .endTime(LocalDateTime.parse("2023-10-12T09:00:00"))
                .day(thursday)
                .amount(1)
                .build();
        worktimes.add(worktime10);
        Worktime worktime11 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-12T09:00:00"))
                .endTime(LocalDateTime.parse("2023-10-12T15:00:00"))
                .day(thursday)
                .amount(1)
                .build();
        worktimes.add(worktime11);

        Worktime worktime12 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-13T00:00:00"))
                .endTime(LocalDateTime.parse("2023-10-13T06:00:00"))
                .day(friday)
                .amount(1)
                .build();
        worktimes.add(worktime12);
        Worktime worktime13 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-13T06:00:00"))
                .endTime(LocalDateTime.parse("2023-10-13T09:00:00"))
                .day(friday)
                .amount(2)
                .build();
        worktimes.add(worktime13);
        Worktime worktime14 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-13T09:00:00"))
                .endTime(LocalDateTime.parse("2023-10-13T15:00:00"))
                .day(friday)
                .amount(1)
                .build();
        worktimes.add(worktime14);

        Worktime worktime15 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-14T00:00:00"))
                .endTime(LocalDateTime.parse("2023-10-14T06:00:00"))
                .day(saturday)
                .amount(1)
                .build();
        worktimes.add(worktime15);
        Worktime worktime16 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-14T06:00:00"))
                .endTime(LocalDateTime.parse("2023-10-14T09:00:00"))
                .day(saturday)
                .amount(1)
                .build();
        worktimes.add(worktime16);

        Worktime worktime17 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-15T00:00:00"))
                .endTime(LocalDateTime.parse("2023-10-15T06:00:00"))
                .day(sunday)
                .amount(4)
                .build();
        worktimes.add(worktime17);
        Worktime worktime18 = Worktime.builder()
                .startTime(LocalDateTime.parse("2023-10-15T06:00:00"))
                .endTime(LocalDateTime.parse("2023-10-15T09:00:00"))
                .day(sunday)
                .amount(2)
                .build();
        worktimes.add(worktime18);
        worktimeRepository.saveAll(worktimes);

        em.clear();
    }
}
