package com.example.team1_be.domain.Schedule.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

import com.example.team1_be.utils.errors.ClientErrorCode;
import com.example.team1_be.utils.errors.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.Apply.Service.ApplyService;
import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.DetailWorktime.Service.DetailWorktimeService;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Schedule.DTO.FixSchedule;
import com.example.team1_be.domain.Schedule.DTO.GetApplies;
import com.example.team1_be.domain.Schedule.DTO.GetDailyFixedApplies;
import com.example.team1_be.domain.Schedule.DTO.GetFixedWeeklySchedule;
import com.example.team1_be.domain.Schedule.DTO.GetWeekStatus;
import com.example.team1_be.domain.Schedule.DTO.LoadLatestSchedule;
import com.example.team1_be.domain.Schedule.DTO.PostApplies;
import com.example.team1_be.domain.Schedule.DTO.RecommendSchedule;
import com.example.team1_be.domain.Schedule.DTO.RecruitSchedule;
import com.example.team1_be.domain.Schedule.DTO.WeeklyScheduleCheck;
import com.example.team1_be.domain.Schedule.Recommend.ScheduleGenerator;
import com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule.RecommendedWeeklySchedule;
import com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule.RecommendedWeeklyScheduleService;
import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApply;
import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApplyService;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserService;
import com.example.team1_be.domain.Week.Service.WeekService;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Worktime.Service.WorktimeService;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
	private final UserService userService;
	private final WeekService weekService;
	private final WorktimeService worktimeService;
	private final DetailWorktimeService detailWorktimeService;
	private final ApplyService applyService;
	private final RecommendedWorktimeApplyService recommendedWorktimeApplyService;
	private final RecommendedWeeklyScheduleService recommendedWeeklyScheduleService;

	public void recruitSchedule(User user, RecruitSchedule.Request request) {
		log.info("스케줄을 모집합니다.");
<<<<<<< HEAD

		if (!user.getIsAdmin()) {
			throw new CustomException(ClientErrorCode.MANAGER_API_REQUEST_ERROR, HttpStatus.FORBIDDEN);	// 매니저 계정만 그룹을 생성할 수 있습니다.
		}

=======
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		Group group = userService.findGroupByUser(user);
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}
		else if (group.getUsers().size() == 1) {
			throw new CustomException(ClientErrorCode.ONLY_MEMBER, HttpStatus.BAD_REQUEST);
		}

		WeekRecruitmentStatus status = weekService.getWeekStatus(group, request.getWeekStartDate());
		if (status != null) {
			throw new CustomException(ClientErrorCode.RECRUITMENT_OBJECT_EXIST, HttpStatus.BAD_REQUEST);
		}

		Week week = weekService.createWeek(group, request.getWeekStartDate());
		List<Worktime> weeklyWorktimes = worktimeService.createWorktimes(week, request.getWorktimes());
		detailWorktimeService.createDays(week.getStartDate(), weeklyWorktimes, request.getAmount());
		log.info("스케줄 모집이 완료되었습니다.");
	}

	public WeeklyScheduleCheck.Response weeklyScheduleCheck(User user, LocalDate request) {
		log.info("주간 스케줄을 확인합니다.");
		Group group = userService.findGroupByUser(user);
<<<<<<< HEAD
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}
		else if (group.getUsers().size() == 1) {
			throw new CustomException(ClientErrorCode.ONLY_MEMBER, HttpStatus.BAD_REQUEST);
		}

		Week week = weekService.findByGroupAndStartDate(group, request);
		weekService.checkAppliable(week);

=======
		Week week = weekService.findByGroupAndStartDate(group, request);
		weekService.checkAppliable(user, week);
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		List<Worktime> weeklyWorktimes = weekService.findWorktimes(week);
		ApplyStatus applyStatus = user.getIsAdmin() ? ApplyStatus.REMAIN : ApplyStatus.FIX;
		Map<String, List<Map<Worktime, List<Apply>>>> weeklyApplies = detailWorktimeService.findAppliesByWorktimeAndDayAndStatus(
			weeklyWorktimes,
			applyStatus);
		log.info("주간 스케줄 확인이 완료되었습니다.");
		return new WeeklyScheduleCheck.Response(weeklyWorktimes, weeklyApplies);
	}

	public GetFixedWeeklySchedule.Response getFixedWeeklySchedule(User user, YearMonth requestMonth, Long userId) {
		log.info("고정 주간 스케줄을 가져옵니다.");
		Group group = userService.findGroupByUser(user);
<<<<<<< HEAD
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}
		else if (group.getUsers().size() == 1) {
			throw new CustomException(ClientErrorCode.ONLY_MEMBER, HttpStatus.BAD_REQUEST);
		}

=======
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		User member = userService.findById(userId);
		SortedMap<LocalDate, List<DetailWorktime>> monthlyDetailWorktimes = detailWorktimeService.findEndedByGroupAndYearMonth(
			group,
			requestMonth);
		SortedMap<LocalDate, List<Apply>> monthlyFixedApplies = applyService.findFixedApplies(
			monthlyDetailWorktimes, member);
		log.info("고정 주간 스케줄 가져오기가 완료되었습니다.");
		return new GetFixedWeeklySchedule.Response(monthlyFixedApplies);
	}

	public GetFixedWeeklySchedule.Response getPersonalWeeklyFixedSchedule(User user, YearMonth requestMonth) {
		log.info("개인 주간 고정 스케줄을 가져옵니다.");
		Group group = userService.findGroupByUser(user);
		SortedMap<LocalDate, List<DetailWorktime>> monthlyDetailWorktimes = detailWorktimeService.findEndedByGroupAndYearMonth(
			group,
			requestMonth);
		SortedMap<LocalDate, List<Apply>> monthlyFixedApplies = applyService.findFixedPersonalApplies(
			monthlyDetailWorktimes, user);
		log.info("개인 주간 고정 스케줄 가져오기가 완료되었습니다.");
		return new GetFixedWeeklySchedule.Response(monthlyFixedApplies);
	}

	public void fixSchedule(User user, FixSchedule.Request request) {
		log.info("스케줄을 고정합니다.");
<<<<<<< HEAD

		if (!user.getIsAdmin()) {
			throw new CustomException(ClientErrorCode.MANAGER_API_REQUEST_ERROR, HttpStatus.FORBIDDEN);	// 매니저 계정만 그룹을 생성할 수 있습니다.
		}

=======
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		Group group = userService.findGroupByUser(user);
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}
		else if (group.getUsers().size() == 1) {
			throw new CustomException(ClientErrorCode.ONLY_MEMBER, HttpStatus.BAD_REQUEST);
		}

		Week week = weekService.findByGroupAndStartDate(group, request.getWeekStartDate());
		weekService.checkAppliable(week);

		List<RecommendedWeeklySchedule> recommendedSchedule = recommendedWeeklyScheduleService.findByWeek(week);
		RecommendedWeeklySchedule recommendedWeeklySchedule = recommendedSchedule.get(request.getSelection());

		weekService.updateWeekStatus(week, WeekRecruitmentStatus.ENDED);

		List<Apply> selectedApplies = new ArrayList<>();
		recommendedWeeklySchedule.getRecommendedWorktimeApplies()
			.forEach(recommendedWorktimeApply ->
				selectedApplies.add(recommendedWorktimeApply.getApply().updateStatus(ApplyStatus.FIX)));
		applyService.createApplies(selectedApplies);

		recommendedSchedule.forEach(x -> recommendedWorktimeApplyService.deleteAll(x.getRecommendedWorktimeApplies()));
		recommendedWeeklyScheduleService.deleteAll(recommendedSchedule);
		log.info("스케줄 고정이 완료되었습니다.");
	}

	public RecommendSchedule.Response recommendSchedule(User user, LocalDate date) {
		log.info("스케줄을 추천합니다.");
<<<<<<< HEAD

		if (!user.getIsAdmin()) {
			throw new CustomException(ClientErrorCode.MANAGER_API_REQUEST_ERROR, HttpStatus.FORBIDDEN);	// 매니저 계정만 그룹을 생성할 수 있습니다.
		}

=======
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		Group group = userService.findGroupByUser(user);
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}
		else if (group.getUsers().size() == 1) {
			throw new CustomException(ClientErrorCode.ONLY_MEMBER, HttpStatus.BAD_REQUEST);
		}

		Week week = weekService.findByGroupAndStartDate(group, date);
		weekService.checkAppliable(week);

		List<Worktime> weeklyWorktimes = worktimeService.findByGroupAndDate(group, date);
		List<DetailWorktime> weeklyDetailWorktimes = detailWorktimeService.findByStartDateAndWorktimes(date,
			weeklyWorktimes);
		List<Apply> weeklyApplies = applyService.findAppliesByWorktimes(weeklyWorktimes);

		Map<Long, Long> requestMap = weeklyDetailWorktimes.stream()
			.collect(Collectors.toMap(DetailWorktime::getId, DetailWorktime::getAmount));

		ScheduleGenerator generator = new ScheduleGenerator(weeklyWorktimes, weeklyApplies, requestMap);
		List<Map<DayOfWeek, SortedMap<Worktime, List<Apply>>>> generatedSchedules = generator.generateSchedule();

		for (Map<DayOfWeek, SortedMap<Worktime, List<Apply>>> generatedSchedule : generatedSchedules) {
			RecommendedWeeklySchedule recommendedWeeklySchedule = recommendedWeeklyScheduleService.creatRecommendedWeeklySchedule(
				week);

			List<RecommendedWorktimeApply> recommendedWorktimeApplies = generatedSchedule.values().stream()
				.flatMap(map -> map.values().stream())
				.flatMap(List::stream)
				.map(apply -> RecommendedWorktimeApply.builder()
					.recommendedWeeklySchedule(recommendedWeeklySchedule)
					.apply(apply)
					.build())
				.collect(Collectors.toList());

			recommendedWorktimeApplyService.createRecommendedWorktimeApplies(recommendedWorktimeApplies);
		}
		log.info("스케줄 추천이 완료되었습니다.");
		return new RecommendSchedule.Response(generatedSchedules);
	}

	public GetDailyFixedApplies.Response getDailyFixedApplies(User user, LocalDate selectedDate) {
		log.info("일일 고정 신청서를 가져옵니다.");
		Group group = userService.findGroupByUser(user);
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}
		else if (group.getUsers().size() == 1) {
			throw new CustomException(ClientErrorCode.ONLY_MEMBER, HttpStatus.BAD_REQUEST);
		}

		WeekRecruitmentStatus status = weekService.getWeekStatus(group, selectedDate);
		if (!user.getIsAdmin() && status.equals(WeekRecruitmentStatus.STARTED)) {
			throw new CustomException(ClientErrorCode.NO_CONFIRMED_SCHEDULE, HttpStatus.BAD_REQUEST);
		}

		Map<Worktime, List<User>> dailyApplyMap = new HashMap<>();
		List<DetailWorktime> detailWorktimes = detailWorktimeService.findByGroupAndDate(group, selectedDate);
		for (DetailWorktime detailWorktime : detailWorktimes) {
			List<User> appliers = applyService.findUsersByWorktimeAndFixedApplies(detailWorktime);
			if (appliers.size() != detailWorktime.getAmount()) {
				throw new CustomException("기존 worktime에서 모집하는 인원을 충족하지 못했습니다.", HttpStatus.NOT_FOUND);
			}
			dailyApplyMap.put(detailWorktime.getWorktime(), appliers);
		}
		log.info("일일 고정 신청서 가져오기가 완료되었습니다.");
		return new GetDailyFixedApplies.Response(dailyApplyMap);
	}

	public LoadLatestSchedule.Response loadLatestSchedule(User user, LocalDate startWeekDate) {
		log.info("최신 스케줄을 불러옵니다.");
<<<<<<< HEAD

		if (!user.getIsAdmin()) {
			throw new CustomException(ClientErrorCode.MANAGER_API_REQUEST_ERROR, HttpStatus.FORBIDDEN);	// 매니저 계정만 그룹을 생성할 수 있습니다.
		}

=======
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		Group group = userService.findGroupByUser(user);
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}
		else if (group.getUsers().size() == 1) {
			throw new CustomException(ClientErrorCode.ONLY_MEMBER, HttpStatus.BAD_REQUEST);
		}

<<<<<<< HEAD
		WeekRecruitmentStatus selectedStatus = weekService.getWeekStatus(group, startWeekDate);
		if (selectedStatus != null) {
			throw new CustomException(ClientErrorCode.RECRUITMENT_OBJECT_EXIST, HttpStatus.BAD_REQUEST);
		}

=======
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		Week latestWeek = weekService.findLatestByGroup(group);
		log.info("최신 스케줄 불러오기가 완료되었습니다.");
		return new LoadLatestSchedule.Response(latestWeek.getWorktimes());
	}

	public GetWeekStatus.Response getWeekStatus(User user, LocalDate startDate) {
		log.info("주간 상태를 가져옵니다.");
		Group group = userService.findGroupByUser(user);
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}
		else if (group.getUsers().size() == 1) {
			throw new CustomException(ClientErrorCode.ONLY_MEMBER, HttpStatus.BAD_REQUEST);
		}

		WeekRecruitmentStatus status = weekService.getWeekStatus(group, startDate);
		log.info("주간 상태 가져오기가 완료되었습니다.");
		return new GetWeekStatus.Response(status);
	}

	public GetApplies.Response getApplies(User user, LocalDate startWeekDate) {
		log.info("신청서를 가져옵니다.");
<<<<<<< HEAD

		if (user.getIsAdmin()) {
			throw new CustomException(ClientErrorCode.MEMBER_API_REQUEST_ERROR, HttpStatus.BAD_REQUEST);	// 알바생만 본인의 스케줄 조회 가능
		}

=======
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		Group group = userService.findGroupByUser(user);
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}

		WeekRecruitmentStatus status = weekService.getWeekStatus(group, startWeekDate);
		if (status == null) {
			throw new CustomException(ClientErrorCode.RECRUITMENT_NOT_STARTED, HttpStatus.BAD_REQUEST);
		}
		else if (status.equals(WeekRecruitmentStatus.ENDED)) {
			throw new CustomException(ClientErrorCode.RECRUITMENT_CLOSED, HttpStatus.BAD_REQUEST);
		}

		List<Worktime> weeklyWorktimes = worktimeService.findByGroupAndDate(group, startWeekDate);

		List<SortedMap<Worktime, Apply>> weeklyApplies = applyService.findByUserAndWorktimeAndDay(user,
			weeklyWorktimes);
		log.info("신청서 가져오기가 완료되었습니다.");
		return new GetApplies.Response(weeklyWorktimes, weeklyApplies);
	}

	public void postApplies(User user, PostApplies.Request requestDTO) {
		log.info("신청서를 게시합니다.");
<<<<<<< HEAD

		if (user.getIsAdmin()) {
			throw new CustomException(ClientErrorCode.MEMBER_API_REQUEST_ERROR, HttpStatus.BAD_REQUEST);	// 알바생만 본인의 스케줄 조회 가능
		}

=======
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		Group group = userService.findGroupByUser(user);
		if (group.getUsers().isEmpty()) {
			throw new CustomException(ClientErrorCode.NO_GROUP, HttpStatus.BAD_REQUEST);
		}

		WeekRecruitmentStatus status = weekService.getWeekStatus(group, requestDTO.getWeekStartDate());
		if (status == null) {
			throw new CustomException(ClientErrorCode.RECRUITMENT_NOT_STARTED, HttpStatus.BAD_REQUEST);
		}
		else if (status.equals(WeekRecruitmentStatus.ENDED)) {
			throw new CustomException(ClientErrorCode.RECRUITMENT_CLOSED, HttpStatus.BAD_REQUEST);
		}

		List<DetailWorktime> previousDetailWorktimes = detailWorktimeService.findByStartDateAndGroup(
			requestDTO.getWeekStartDate(), group);
		List<DetailWorktime> appliedDetailWorktimes = detailWorktimeService.findByStartDateAndWorktimes(
			requestDTO.toWeeklyApplies());
		applyService.updateApplies(user, previousDetailWorktimes, appliedDetailWorktimes);
		log.info("신청서 게시가 완료되었습니다.");
	}
}