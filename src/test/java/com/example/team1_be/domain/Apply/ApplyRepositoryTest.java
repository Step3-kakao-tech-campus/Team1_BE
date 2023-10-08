package com.example.team1_be.domain.Apply;

import com.example.team1_be.BaseTest;
import com.example.team1_be.domain.Day.DayRepository;
import com.example.team1_be.domain.Group.GroupRepository;
import com.example.team1_be.domain.Member.Member;
import com.example.team1_be.domain.Member.MemberRepository;
import com.example.team1_be.domain.Notification.NotificationRepository;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.Substitute.SubstituteRepository;
import com.example.team1_be.domain.User.UserRepository;
import com.example.team1_be.domain.Week.WeekRepository;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ApplyRepositoryTest extends BaseTest {

    public ApplyRepositoryTest(UserRepository userRepository, GroupRepository groupRepository, MemberRepository memberRepository, NotificationRepository notificationRepository, DayRepository dayRepository, ApplyRepository applyRepository, WeekRepository weekRepository, WorktimeRepository worktimeRepository, ScheduleRepository scheduleRepository, SubstituteRepository substituteRepository, EntityManager em) {
        super(userRepository, groupRepository, memberRepository, notificationRepository, dayRepository, applyRepository, weekRepository, worktimeRepository, scheduleRepository, substituteRepository, em);
    }

    @DisplayName("근무 시간별 신청인원 부족한 사람 조회")
    @Test
    void test1() {
        List<Worktime> worktimes = worktimeRepository.findAll();
        List<Apply> applyList = applyRepository.findAll();

        List<Integer> collect = worktimes.stream().map(
                        x -> x.getAmount() - applyRepository.findappliesByWorktimeId(x.getId()).size())
                .collect(Collectors.toList());
        collect.forEach(System.out::println);
    }
    @DisplayName("근무 시간 할당 최적화")
    @Test
    void test2() {
        /**
         * 신청의 우선순위를 결정
         * 지원한 시간대의 필요 인원 (요구량-지원자 수)이 클수록 높음
         * 할당을 통해서 변화되는 점
         * 필요 상태
         * 요구 인원수 테이블 <근무 일정 : 요구 인원수>
         * 지원 현황 및 우선순위 <근무 일정 : 지원자 수 : 우선 순위(요구량-지원자 수)>
         * 신청 현황 <신청 ID : 신청 상태>
         * 알고리즘
         * 1. 신청 우선순위 pop
         * 2. 요구 인원수 테이블에서 해당 근무 일정 요구 인원수 -1
         * 3. 신청 상태 변경 FIX -> REMAIN
         */
        List<Worktime> worktimes = worktimeRepository.findAll();
        List<Apply> applyList = applyRepository.findAll();
        int worktimeSize = worktimes.size();
        int applySize = applyList.size();
        int i;

        Map<Long,Integer> requestMap = new HashMap<>();
        for(i=0; i< worktimeSize; i++) {
            Worktime worktime = worktimes.get(i);
            requestMap.put(worktime.getId(),worktime.getAmount());
        }

        Long [][] priorityTable = new Long[worktimeSize][2];
        for(i = 0; i< worktimeSize; i++) {
            Worktime worktime = worktimes.get(i);
            priorityTable[i][0] = worktime.getId();
            priorityTable[i][1] = (long) (worktime.getAmount() - applyRepository.findappliesByWorktimeId(worktime.getId()).size());
        }
        Arrays.sort(priorityTable, (a,b)->Long.compare(b[1],a[1])); // 여유 인원이 적은 곳 부터 할당하기 위해서 정렬

        Long [][] applyTable = new Long[applySize][3];
        for(i=0;i<applySize;i++) {
            Apply apply = applyList.get(i);
            applyTable[i][0] = apply.getId();
            applyTable[i][1] = apply.getWorktime().getId();
            applyTable[i][2] = Arrays.stream(priorityTable)
                    .filter(x->x[0]==apply.getWorktime().getId())
                    .findFirst().get()[1];
        }
        Arrays.sort(applyTable, (a,b)->Long.compare(b[2],a[2]));

        /**
         * applyTable에서 하나씩 추출해서 할당
         * 할당할때 이미 완료된 업무의 경우 패스
         */
        for(i=0;i<applySize;i++){
            Long worktimeId = applyTable[i][1];
            Integer remain = requestMap.get(worktimeId);
            if (remain > 0) {
                requestMap.put(worktimeId, remain -1);
                Apply apply =  applyRepository.findById(applyTable[i][0]).orElse(null);
                Apply updateApply = Apply.builder()
                        .id(apply.getId())
                        .member(apply.getMember())
                        .worktime(apply.getWorktime())
                        .state(ApplyType.FIX)
                        .build();
                applyRepository.save(updateApply);
            }
        }

        System.out.println("테스트");
        for(int j=0;j<priorityTable.length;j++) {
            for(int k=0;k<priorityTable[j].length;k++){
                System.out.print(priorityTable[j][k] + " ");
            }
            System.out.println();
        }
        System.out.println("신청 현황");
        for(int j=0;j<applyTable.length;j++) {
            for(int k=0;k<applyTable[j].length;k++){
                System.out.print(applyTable[j][k] + " ");
            }
            System.out.println();
        }
        System.out.println("신청 결과");
        System.out.println(requestMap);
        System.out.println("전체 신청 현황");
        List<Apply> fixedApplies = applyRepository.findAppliesByStatus(ApplyType.FIX);
        System.out.println("전체 요구 근무 슬롯 수 : " + worktimeRepository.findCountByWeekId(1L));
        System.out.println("완료된 신청 수 : " + fixedApplies.size());
    }
}