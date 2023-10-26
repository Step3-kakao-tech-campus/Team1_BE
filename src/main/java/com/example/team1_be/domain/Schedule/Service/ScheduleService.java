package com.example.team1_be.domain.Schedule.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyService;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Day.DayService;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.Service.GroupService;
import com.example.team1_be.domain.Schedule.DTO.FixSchedule;
import com.example.team1_be.domain.Schedule.DTO.GetDailyFixedApplies;
import com.example.team1_be.domain.Schedule.DTO.GetFixedWeeklySchedule;
import com.example.team1_be.domain.Schedule.DTO.GetWeekStatus;
import com.example.team1_be.domain.Schedule.DTO.LoadLatestSchedule;
import com.example.team1_be.domain.Schedule.DTO.RecommendSchedule;
import com.example.team1_be.domain.Schedule.DTO.RecruitSchedule;
import com.example.team1_be.domain.Schedule.DTO.WeeklyScheduleCheck;
import com.example.team1_be.domain.Schedule.Recommend.SchduleGenerator;
import com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule.RecommendedWeeklySchedule;
import com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule.RecommendedWeeklyScheduleService;
import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApply;
import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApplyService;
import com.example.team1_be.domain.Schedule.Schedule;
import com.example.team1_be.domain.Schedule.ScheduleRepository;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.User.UserService;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Week.WeekRecruitmentStatus;
import com.example.team1_be.domain.Week.WeekService;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeService;
import com.example.team1_be.utils.errors.exception.CustomException;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {
	private final int NUM_DAYS_OF_WEEK = 7;

	private final UserService userService;
	private final GroupService groupService;
	private final ScheduleRepository scheduleRepository;
	private final WeekService weekService;
	private final DayService dayService;
	private final WorktimeService worktimeService;
	private final ApplyService applyService;
	private final RecommendedWorktimeApplyService recommendedWorktimeApplyService;
	private final RecommendedWeeklyScheduleService recommendedWeeklyScheduleService;

	@Transactional
	public void recruitSchedule(User user, RecruitSchedule.Request request) {
		if (request.getWeeklyAmount().size() != NUM_DAYS_OF_WEEK) {
			throw new CustomException("모든 요일에 대한 정보가 없습니다.", HttpStatus.BAD_REQUEST);
		}

		Group group = userService.findGroupByUser(user);

		Schedule schedule = Schedule.builder()
			.group(group)
			.build();
		scheduleRepository.save(schedule);

		Week week = weekService.createWeek(schedule, request.getWeekStartDate());

		List<RecruitSchedule.Request.DailySchedule> weeklyAmount = request.getWeeklyAmount();
		List<Day> days = IntStream.range(1, weeklyAmount.size() + 1)
			.mapToObj(dayOfWeek -> Day.builder()
				.week(week)
				.dayOfWeek(dayOfWeek)
				.build()
			).collect(Collectors.toList());
		dayService.createDays(days);

		List<Worktime> worktimeList = new ArrayList<>();
		IntStream.range(0, days.size())
			.forEach(dayIdx -> weeklyAmount.get(dayIdx)
				.getDailySchedules()
				.forEach(worktime -> worktimeList.add(Worktime.builder()
					.title(worktime.getTitle())
					.startTime(worktime.getStartTime())
					.endTime(worktime.getEndTime())
					.amount(worktime.getAmount())
					.day(days.get(dayIdx))
					.build())));

		worktimeService.createWorktimes(worktimeList);
	}

	public WeeklyScheduleCheck.Response weeklyScheduleCheck(User user, LocalDate request) {
		Group group = userService.findGroupByUser(user);

		Schedule schedule = findByGroup(group);

		Week week = user.getIsAdmin() ?
			weekService.findByScheduleIdStartDateAndStatus(schedule, request, WeekRecruitmentStatus.STARTED) :
			weekService.findByScheduleIdStartDateAndStatus(schedule, request, WeekRecruitmentStatus.ENDED);

		List<Day> days = dayService.findByWeek(week);

		List<List<Worktime>> weeklyWorktime = worktimeService.findWorktimesByDays(days);

		List<List<List<Apply>>> applyList = weeklyWorktime.stream()
			.map(worktimes -> worktimes.stream()
				.map(applyService::findAppliesByWorktime)
				.collect(Collectors.toList())).collect(Collectors.toList());

		return new WeeklyScheduleCheck.Response(weeklyWorktime, applyList);
	}

	public GetFixedWeeklySchedule.Response getFixedWeeklySchedule(User user, YearMonth requestMonth, Long memberId) {
		User member = userService.findById(memberId);
		Schedule schedule = findByGroup(member.getGroup());

		LocalDate date = LocalDate.of(requestMonth.getYear(), requestMonth.getMonth(), 1);
		LocalDate toDate = LocalDate.of(requestMonth.getYear(), requestMonth.getMonth(), 1).plusMonths(1);
		List<Week> weeks = weekService.findByScheduleAndYearMonthAndStatus(date, toDate, schedule,
			WeekRecruitmentStatus.ENDED);
		List<Worktime> memberWorktimes = applyService.findWorktimesByYearMonthAndStatusAndUser(date, toDate, member,
			ApplyStatus.FIX);
		Double monthly = memberWorktimes.stream()
			.mapToDouble(
				worktime -> Duration.between(worktime.getStartTime(), worktime.getEndTime()).getSeconds() / 3600)
			.reduce(0D, Double::sum);

		return new GetFixedWeeklySchedule.Response(memberWorktimes, monthly, monthly / weeks.size());
	}

	@Transactional
	public RecommendSchedule.Response recommendSchedule(User user, LocalDate date) {
		Group group = userService.findGroupByUser(user);

		Schedule schedule = findByGroup(group);

		List<Worktime> weeklyWorktimes = worktimeService.findByStartDateAndSchedule(date, schedule);

		List<Apply> weeklyApplies = applyService.findAppliesByWorktimes(weeklyWorktimes);

		Map<Long, Integer> requestMap = weeklyWorktimes.stream()
			.collect(Collectors.toMap(Worktime::getId, Worktime::getAmount));

		SchduleGenerator generator = new SchduleGenerator(weeklyApplies, requestMap);
		List<List<Apply>> generatedSchedules = generator.generateSchedule();

		for (List<Apply> generatedSchedule : generatedSchedules) {
			RecommendedWeeklySchedule weeklySchedule = RecommendedWeeklySchedule.builder()
				.user(user)
				.build();
			weeklySchedule = recommendedWeeklyScheduleService.creatRecommendedWeeklySchedule(weeklySchedule);

			List<RecommendedWorktimeApply> recommendedWorktimeApplies = new ArrayList<>();
			for (Worktime worktime : weeklyWorktimes) {
				List<Apply> applies = generatedSchedule.stream()
					.filter(x -> x.getWorktime().getId().equals(worktime.getId()))
					.collect(Collectors.toList());

				for (Apply apply : applies) {
					recommendedWorktimeApplies.add(RecommendedWorktimeApply.builder()
						.recommendedWeeklySchedule(weeklySchedule)
						.apply(apply)
						.build());
				}
			}

			recommendedWorktimeApplyService.createRecommendedWorktimeApplies(recommendedWorktimeApplies);
		}
		return new RecommendSchedule.Response(weeklyWorktimes, generatedSchedules);
	}

	@Transactional
	public void fixSchedule(User user, FixSchedule.Request request) {
		List<RecommendedWeeklySchedule> recommendedSchedule = recommendedWeeklyScheduleService.findByUser(user);
		RecommendedWeeklySchedule recommendedWeeklySchedule = recommendedSchedule.get(request.getSelection());

		Week week = recommendedWeeklySchedule.getRecommendedWorktimeApplies()
			.get(0)
			.getApply()
			.getWorktime()
			.getDay()
			.getWeek();

		weekService.updateWeekStatus(week, WeekRecruitmentStatus.ENDED);

		List<Apply> selectedApplies = new ArrayList<>();
		recommendedWeeklySchedule.getRecommendedWorktimeApplies()
			.forEach(recommendedWorktimeApply ->
				selectedApplies.add(recommendedWorktimeApply.getApply().updateStatus(ApplyStatus.FIX)));
		applyService.createApplies(selectedApplies);

		recommendedSchedule.forEach(x -> recommendedWorktimeApplyService.deleteAll(x.getRecommendedWorktimeApplies()));
		recommendedWeeklyScheduleService.deleteAll(recommendedSchedule);
	}

	public GetDailyFixedApplies.Response getDailyFixedApplies(User user, LocalDate selectedDate) {
		Group group = userService.findGroupByUser(user);
		Schedule schedule = findByGroup(group);

		LocalDate date = selectedDate.minusDays(selectedDate.getDayOfWeek().getValue() - 1);
		int dayOfWeek = selectedDate.getDayOfWeek().getValue();
		List<Worktime> worktimes = worktimeService.findBySpecificDateAndSchedule(date, dayOfWeek, schedule);

		List<List<Apply>> dailyApplies = new ArrayList<>();
		for (Worktime worktime : worktimes) {
			List<Apply> applies = applyService.findFixedAppliesByWorktime(worktime);
			if (applies.size() != worktime.getAmount()) {
				throw new NotFoundException("기존 worktime에서 모집하는 인원을 충족하지 못했습니다.");
			}
			dailyApplies.add(applies);
		}
		return new GetDailyFixedApplies.Response(worktimes, dailyApplies);
	}

	public GetFixedWeeklySchedule.Response getUsersFixedWeeklySchedule(User user, YearMonth requestMonth) {
		Group group = userService.findGroupByUser(user);
		Schedule schedule = findByGroup(group);

		LocalDate date = LocalDate.of(requestMonth.getYear(), requestMonth.getMonth(), 1);
		LocalDate toDate = LocalDate.of(requestMonth.getYear(), requestMonth.getMonth(), 1).plusMonths(1);
		List<Week> weeks = weekService.findByScheduleAndYearMonthAndStatus(date, toDate, schedule,
			WeekRecruitmentStatus.ENDED);
		List<Worktime> memberWorktimes = applyService.findWorktimesByYearMonthAndStatusAndUser(date, toDate, user,
			ApplyStatus.FIX);
		Double monthly = memberWorktimes.stream()
			.mapToDouble(
				worktime -> Duration.between(worktime.getStartTime(), worktime.getEndTime()).getSeconds() / 3600)
			.reduce(0D, Double::sum);

		return new GetFixedWeeklySchedule.Response(memberWorktimes, monthly, monthly / weeks.size());
	}

	public LoadLatestSchedule.Response loadLatestSchedule(User user, LocalDate startWeekDate) {
		Group group = userService.findGroupByUser(user);

		Schedule schedule = findByGroup(group);

		List<Week> latestWeeks = weekService.findLatestByScheduleAndStatus(schedule, WeekRecruitmentStatus.ENDED);
		if (latestWeeks.isEmpty()) {
			throw new NotFoundException("최근 스케줄을 찾을 수 없습니다.");
		}

		List<Worktime> latestWorktimes = latestWeeks.get(0).getDay().get(0).getWorktimes();

		return new LoadLatestSchedule.Response(latestWorktimes);
	}

	public GetWeekStatus.Response getWeekStatus(User user, LocalDate startWeekDate) {
		Group group = userService.findGroupByUser(user);

		Schedule schedule = findByGroup(group);
		Week week = weekService.findByScheduleAndStartDate(schedule, startWeekDate);

		if (week == null) {
			return new GetWeekStatus.Response(null);
		} else {
			return new GetWeekStatus.Response(week.getStatus());
		}
	}

	public Schedule findByGroup(Group group) {
		return scheduleRepository.findByGroup(group)
			.orElseThrow(() -> new NotFoundException("스케줄을 찾을 수 없습니다."));
	}
}
