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
import com.example.team1_be.domain.Week.WeekType;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
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
        User admin = User.builder().kakaoId(1L).name("dlwogns").phoneNumber("010-1111-1111").build();
        users.add(admin);
        User user1 = User.builder().kakaoId(2L).name("dksgkswn").phoneNumber("010-2222-2222").build();
        users.add(user1);
        User user2 = User.builder().kakaoId(3L).name("chldmswls").phoneNumber("010-3333-3333").build();
        users.add(user2);
        User user3 = User.builder().kakaoId(4L).name("dlguswl").phoneNumber("010-4444-4444").build();
        users.add(user3);
        User user4 = User.builder().kakaoId(5L).name("ckwldnjs").phoneNumber("010-5555-5555").build();
        users.add(user4);
        User user5 = User.builder().kakaoId(6L).name("alsgkfls").phoneNumber("010-6666-6666").build();
        users.add(user5);
        userRepository.saveAll(users);

        Group group = Group.builder().address("부산광역시").name("맘스터치").phoneNumber("010-8888-8888").build();

        group = groupRepository.save(group);

        List<Member> members = new ArrayList<>();
        Member member = Member.builder().user(admin).group(group).isAdmin(true).build();
        members.add(member);
        Member member1 = Member.builder().user(user1).group(group).isAdmin(false).build();
        members.add(member1);
        Member member2 = Member.builder().user(user2).group(group).isAdmin(false).build();
        members.add(member2);
        Member member3 = Member.builder().user(user3).group(group).isAdmin(false).build();
        members.add(member3);
        Member member4 = Member.builder().user(user4).group(group).isAdmin(false).build();
        members.add(member4);
        Member member5 = Member.builder().user(user5).group(group).isAdmin(false).build();
        members.add(member5);
        memberRepository.saveAll(members);

        Schedule schedule = Schedule.builder().group(group).build();
        scheduleRepository.save(schedule);

        List<Notification> notifications = new ArrayList<>();
        Notification startNoti = Notification.builder().user(user2).type(NotificationType.START).content("시작 알림").isRead(false).build();
        notifications.add(startNoti);

        Notification endNoti = Notification.builder().user(user2).type(NotificationType.END).content("종료 알림").isRead(false).build();
        notifications.add(endNoti);

        Notification etcNoti = Notification.builder().user(user2).type(NotificationType.ETC).content("기타 알림").isRead(false).build();
        notifications.add(etcNoti);
        notificationRepository.saveAll(notifications);
        
        Week week = Week.builder().schedule(schedule).status(WeekType.STARTED).startDate(LocalDate.now()).build();
        weekRepository.save(week);

        List<Day> days = new ArrayList<>();
        Day monday = Day.builder().dayOfWeek(1).week(week).build();
        days.add(monday);
        Day tuesday = Day.builder().dayOfWeek(2).week(week).build();
        days.add(tuesday);
        Day wednesday = Day.builder().dayOfWeek(3).week(week).build();
        days.add(wednesday);
        Day thursday = Day.builder().dayOfWeek(4).week(week).build();
        days.add(thursday);
        Day friday = Day.builder().dayOfWeek(5).week(week).build();
        days.add(friday);
        Day saturday = Day.builder().dayOfWeek(6).week(week).build();
        days.add(saturday);
        Day sunday = Day.builder().dayOfWeek(7).week(week).build();
        days.add(sunday);
        dayRepository.saveAll(days);

        List<Worktime> worktimes = new ArrayList<>();
        Worktime worktime1 = Worktime.builder().startTime(LocalTime.parse("2023-10-09T00:00:00")).endTime(LocalTime.parse("2023-10-09T06:00:00")).day(monday).amount(3).build();
        worktimes.add(worktime1);
        Worktime worktime2 = Worktime.builder().startTime(LocalTime.parse("2023-10-09T06:00:00")).endTime(LocalTime.parse("2023-10-09T09:00:00")).day(monday).amount(2).build();
        worktimes.add(worktime2);
        Worktime worktime3 = Worktime.builder().startTime(LocalTime.parse("2023-10-09T09:00:00")).endTime(LocalTime.parse("2023-10-09T15:00:00")).day(monday).amount(1).build();
        worktimes.add(worktime3);

        Worktime worktime4 = Worktime.builder().startTime(LocalTime.parse("2023-10-10T00:00:00")).endTime(LocalTime.parse("2023-10-10T06:00:00")).day(tuesday).amount(2).build();
        worktimes.add(worktime4);
        Worktime worktime5 = Worktime.builder().startTime(LocalTime.parse("2023-10-10T06:00:00")).endTime(LocalTime.parse("2023-10-10T09:00:00")).day(tuesday).amount(2).build();
        worktimes.add(worktime5);
        Worktime worktime6 = Worktime.builder().startTime(LocalTime.parse("2023-10-10T09:00:00")).endTime(LocalTime.parse("2023-10-10T15:00:00")).day(tuesday).amount(2).build();
        worktimes.add(worktime6);

        Worktime worktime7 = Worktime.builder().startTime(LocalTime.parse("2023-10-11T00:00:00")).endTime(LocalTime.parse("2023-10-11T06:00:00")).day(wednesday).amount(3).build();
        worktimes.add(worktime7);
        Worktime worktime8 = Worktime.builder().startTime(LocalTime.parse("2023-10-11T06:00:00")).endTime(LocalTime.parse("2023-10-11T09:00:00")).day(wednesday).amount(3).build();
        worktimes.add(worktime8);

        Worktime worktime9 = Worktime.builder().startTime(LocalTime.parse("2023-10-12T00:00:00")).endTime(LocalTime.parse("2023-10-12T06:00:00")).day(thursday).amount(1).build();
        worktimes.add(worktime9);
        Worktime worktime10 = Worktime.builder().startTime(LocalTime.parse("2023-10-12T06:00:00")).endTime(LocalTime.parse("2023-10-12T09:00:00")).day(thursday).amount(1).build();
        worktimes.add(worktime10);
        Worktime worktime11 = Worktime.builder().startTime(LocalTime.parse("2023-10-12T09:00:00")).endTime(LocalTime.parse("2023-10-12T15:00:00")).day(thursday).amount(1).build();
        worktimes.add(worktime11);

        Worktime worktime12 = Worktime.builder().startTime(LocalTime.parse("2023-10-13T00:00:00")).endTime(LocalTime.parse("2023-10-13T06:00:00")).day(friday).amount(1).build();
        worktimes.add(worktime12);
        Worktime worktime13 = Worktime.builder().startTime(LocalTime.parse("2023-10-13T06:00:00")).endTime(LocalTime.parse("2023-10-13T09:00:00")).day(friday).amount(2).build();
        worktimes.add(worktime13);
        Worktime worktime14 = Worktime.builder().startTime(LocalTime.parse("2023-10-13T09:00:00")).endTime(LocalTime.parse("2023-10-13T15:00:00")).day(friday).amount(1).build();
        worktimes.add(worktime14);

        Worktime worktime15 = Worktime.builder().startTime(LocalTime.parse("2023-10-14T00:00:00")).endTime(LocalTime.parse("2023-10-14T06:00:00")).day(saturday).amount(1).build();
        worktimes.add(worktime15);
        Worktime worktime16 = Worktime.builder().startTime(LocalTime.parse("2023-10-14T06:00:00")).endTime(LocalTime.parse("2023-10-14T09:00:00")).day(saturday).amount(1).build();
        worktimes.add(worktime16);

        Worktime worktime17 = Worktime.builder().startTime(LocalTime.parse("2023-10-15T00:00:00")).endTime(LocalTime.parse("2023-10-15T06:00:00")).day(sunday).amount(2).build();
        worktimes.add(worktime17);
        Worktime worktime18 = Worktime.builder().startTime(LocalTime.parse("2023-10-15T06:00:00")).endTime(LocalTime.parse("2023-10-15T09:00:00")).day(sunday).amount(2).build();
        worktimes.add(worktime18);
        worktimeRepository.saveAll(worktimes);

        List<Apply> applies = new ArrayList<>();
        Apply apply1 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime1).build();
        applies.add(apply1);
        Apply apply58 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime2).build();
        applies.add(apply58);
        Apply apply2 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime3).build();
        applies.add(apply2);
        Apply apply3 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime3).build();
        applies.add(apply3);
        Apply apply4 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime3).build();
        applies.add(apply4);
        Apply apply5 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime7).build();
        applies.add(apply5);
        Apply apply57 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime8).build();
        applies.add(apply57);
        Apply apply6 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime9).build();
        applies.add(apply6);
        Apply apply7 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime10).build();
        applies.add(apply7);
        Apply apply54 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime12).build();
        applies.add(apply54);
        Apply apply55 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime13).build();
        applies.add(apply55);
        Apply apply56 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime14).build();
        applies.add(apply56);
        Apply apply50 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime15).build();
        applies.add(apply50);
        Apply apply51 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime16).build();
        applies.add(apply51);
        Apply apply52 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime17).build();
        applies.add(apply52);
        Apply apply53 = Apply.builder().status(ApplyStatus.REMAIN).member(member1).worktime(worktime18).build();
        applies.add(apply53);

        Apply apply59 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime2).build();
        applies.add(apply59);
        Apply apply8 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime3).build();
        applies.add(apply8);
        Apply apply9 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime4).build();
        applies.add(apply9);
        Apply apply10 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime5).build();
        applies.add(apply10);
        Apply apply61 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime6).build();
        applies.add(apply61);
        Apply apply62 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime8).build();
        applies.add(apply62);
        Apply apply11 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime9).build();
        applies.add(apply11);
        Apply apply12 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime10).build();
        applies.add(apply12);
        Apply apply60 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime11).build();
        applies.add(apply60);
        Apply apply13 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime12).build();
        applies.add(apply13);
        Apply apply14 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime13).build();
        applies.add(apply14);
        Apply apply15 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime14).build();
        applies.add(apply15);
        Apply apply16 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime15).build();
        applies.add(apply16);
        Apply apply17 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime16).build();
        applies.add(apply17);
        Apply apply18 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime17).build();
        applies.add(apply18);
        Apply apply19 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime18).build();
        applies.add(apply19);

        Apply apply20 = Apply.builder().status(ApplyStatus.REMAIN).member(member3).worktime(worktime1).build();
        applies.add(apply20);
        Apply apply21 = Apply.builder().status(ApplyStatus.REMAIN).member(member3).worktime(worktime3).build();
        applies.add(apply21);
        Apply apply22 = Apply.builder().status(ApplyStatus.REMAIN).member(member3).worktime(worktime4).build();
        applies.add(apply22);
        Apply apply23 = Apply.builder().status(ApplyStatus.REMAIN).member(member3).worktime(worktime5).build();
        applies.add(apply23);
        Apply apply24 = Apply.builder().status(ApplyStatus.REMAIN).member(member3).worktime(worktime7).build();
        applies.add(apply24);
        Apply apply25 = Apply.builder().status(ApplyStatus.REMAIN).member(member3).worktime(worktime9).build();
        applies.add(apply25);
        Apply apply26 = Apply.builder().status(ApplyStatus.REMAIN).member(member3).worktime(worktime10).build();
        applies.add(apply26);
        Apply apply27 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime15).build();
        applies.add(apply27);
        Apply apply28 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime17).build();
        applies.add(apply28);
        Apply apply29 = Apply.builder().status(ApplyStatus.REMAIN).member(member2).worktime(worktime18).build();
        applies.add(apply29);

        Apply apply30 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime1).build();
        applies.add(apply30);
        Apply apply31 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime3).build();
        applies.add(apply31);
        Apply apply32 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime4).build();
        applies.add(apply32);
        Apply apply33 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime5).build();
        applies.add(apply33);
        Apply apply63 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime6).build();
        applies.add(apply63);
        Apply apply34 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime7).build();
        applies.add(apply34);
        Apply apply64 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime8).build();
        applies.add(apply64);
        Apply apply35 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime9).build();
        applies.add(apply35);
        Apply apply36 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime10).build();
        applies.add(apply36);
        Apply apply37 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime15).build();
        applies.add(apply37);
        Apply apply38 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime16).build();
        applies.add(apply38);
        Apply apply39 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime17).build();
        applies.add(apply39);
        Apply apply40 = Apply.builder().status(ApplyStatus.REMAIN).member(member4).worktime(worktime18).build();
        applies.add(apply40);

        Apply apply41 = Apply.builder().status(ApplyStatus.REMAIN).member(member5).worktime(worktime1).build();
        applies.add(apply41);
        Apply apply42 = Apply.builder().status(ApplyStatus.REMAIN).member(member5).worktime(worktime3).build();
        applies.add(apply42);
        Apply apply43 = Apply.builder().status(ApplyStatus.REMAIN).member(member5).worktime(worktime4).build();
        applies.add(apply43);
        Apply apply44 = Apply.builder().status(ApplyStatus.REMAIN).member(member5).worktime(worktime5).build();
        applies.add(apply44);
        Apply apply45 = Apply.builder().status(ApplyStatus.REMAIN).member(member5).worktime(worktime7).build();
        applies.add(apply45);
        Apply apply46 = Apply.builder().status(ApplyStatus.REMAIN).member(member5).worktime(worktime9).build();
        applies.add(apply46);
        Apply apply47 = Apply.builder().status(ApplyStatus.REMAIN).member(member5).worktime(worktime10).build();
        applies.add(apply47);
        Apply apply48 = Apply.builder().status(ApplyStatus.REMAIN).member(member5).worktime(worktime15).build();
        applies.add(apply48);
        Apply apply49 = Apply.builder().status(ApplyStatus.REMAIN).member(member5).worktime(worktime17).build();
        applies.add(apply49);
        applyRepository.saveAll(applies);

        em.clear();
    }
}
