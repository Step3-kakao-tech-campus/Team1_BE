package com.example.team1_be;

import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;

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

    public void resetRepository() {
        em.clear();

        userRepository.deleteAll();
        groupRepository.deleteAll();
        memberRepository.deleteAll();
        notificationRepository.deleteAll();
        dayRepository.deleteAll();
        applyRepository.deleteAll();
        weekRepository.deleteAll();
        worktimeRepository.deleteAll();
        scheduleRepository.deleteAll();
        substituteRepository.deleteAll();


        em.createNativeQuery(
                "ALTER TABLE users ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE groups ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE member ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE notification ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE days ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE apply ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE week ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE worktime ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE schedule ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();
        em.createNativeQuery(
                "ALTER TABLE substitute ALTER COLUMN `id` RESTART WITH 1")
                .executeUpdate();

        em.clear();
    }
}
