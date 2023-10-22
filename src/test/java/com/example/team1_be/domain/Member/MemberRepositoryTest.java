package com.example.team1_be.domain.Member;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest extends BaseTest {

    public MemberRepositoryTest(UserRepository userRepository, GroupRepository groupRepository, MemberRepository memberRepository, NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository, SubstituteRepository substituteRepository, EntityManager em) {
        super(userRepository, groupRepository, memberRepository, notificationRepository, dayRepository, applyRepository, weekRepository, worktimeRepository, scheduleRepository, substituteRepository, em);
    }

    @DisplayName("멤버 조회")
    @Test
    void test1() {
        assertThat(memberRepository.findById(1L).orElse(null))
                .isNotEqualTo(null);
    }

    @DisplayName("멤버 전체 조회")
    @Test
    void test2() throws JsonProcessingException {
        Member me = memberRepository.findById(1L).orElse(null);
        List<Member> members = memberRepository.findAllByGroup(me.getGroup());

        ObjectMapper om = new ObjectMapper();

        for(Member member:members) {
            String result = om.writeValueAsString(member.getUser().getName());
            System.out.println(result);
        }
    }
}