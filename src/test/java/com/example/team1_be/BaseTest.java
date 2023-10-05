package com.example.team1_be;

import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @AfterEach
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

    @BeforeEach
    public void initializeRepository() {
        createUser();
        createGroup();
    }

    private void createGroup() {
        groupRepository.save(Group.builder()
                .address("부산광역시")
                .name("맘스터치")
                .phoneNumber("010-8888-8888")
                .build());
    }

    private void createUser() {
        userRepository.save(User.builder()
                .kakaoId(1L)
                .name("dlwogns")
                .phoneNumber("010-1111-1111")
                .build());
        userRepository.save(User.builder()
                .kakaoId(2L)
                .name("dksgkswn")
                .phoneNumber("010-2222-2222")
                .build());
        userRepository.save(User.builder()
                .kakaoId(3L)
                .name("chldmswls")
                .phoneNumber("010-3333-3333")
                .build());
        userRepository.save(User.builder()
                .kakaoId(4L)
                .name("dlguswl")
                .phoneNumber("010-4444-4444")
                .build());
        userRepository.save(User.builder()
                .kakaoId(5L)
                .name("ckwldnjs")
                .phoneNumber("010-5555-5555")
                .build());
        userRepository.save(User.builder()
                .kakaoId(6L)
                .name("alsgkfls")
                .phoneNumber("010-6666-6666")
                .build());
    }
}
