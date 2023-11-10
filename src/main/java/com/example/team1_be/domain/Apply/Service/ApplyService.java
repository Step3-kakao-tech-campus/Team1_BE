package com.example.team1_be.domain.Apply.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.example.team1_be.utils.errors.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ApplyService {
	private final ApplyReadOnlyService readOnlyService;
	private final ApplyWriteOnlyService writeOnlyService;

	public List<Apply> findAppliesByWorktimes(List<Worktime> worktimes) {
		List<Long> worktimeIds = worktimes.stream()
<<<<<<< HEAD
				.map(Worktime::getId)
				.collect(Collectors.toList());
=======
			.map(Worktime::getId)
			.collect(Collectors.toList());
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		log.info("{}개의 근무 시간에 대한 신청 정보를 조회합니다.", worktimeIds.size());
		return readOnlyService.findAppliesByWorktimes(worktimeIds);
	}

	public void createApplies(List<Apply> applies) {
		log.info("{}개의 신청 정보를 생성합니다.", applies.size());
		writeOnlyService.createApplies(applies);
	}

	public SortedMap<LocalDate, List<Apply>> findFixedApplies(
			SortedMap<LocalDate, List<DetailWorktime>> monthlyDetailWorktimes, User user) {
		SortedMap<LocalDate, List<Apply>> monthlyApplies = new TreeMap<>((s1, s2) -> s1.compareTo(s2));
		for (LocalDate date : monthlyDetailWorktimes.keySet()) {
			List<Apply> applies = readOnlyService.findByUserAndDateAndStatus(user, date, ApplyStatus.FIX);
			if (applies.isEmpty()) {
				continue;
			}
			monthlyApplies.put(date, applies);
		}

		if (monthlyApplies.isEmpty()) {
			throw new CustomException("확정된 스케줄이 없습니다.", HttpStatus.NOT_FOUND);
		}
		return monthlyApplies;
	}

	public List<User> findUsersByWorktimeAndApplyStatus(DetailWorktime worktime, ApplyStatus status) {
		List<User> users = readOnlyService.findUsersByWorktimeAndApplyStatus(worktime, status);
		if (users.isEmpty()) {
			log.warn("근무 시간 ID: {}, 상태: {}에 따른 신청자를 찾을 수 없습니다.", worktime.getId(), status);
<<<<<<< HEAD
			throw new CustomException("확정된 신청자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
=======
			throw new NotFoundException("확정된 신청자를 찾을 수 없습니다.");
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		}
		log.info("근무 시간 ID: {}, 상태: {}에 따른 신청자를 조회하였습니다.", worktime.getId(), status);
		return users;
	}

	public List<User> findUsersByWorktimeAndFixedApplies(DetailWorktime worktime) {
		return findUsersByWorktimeAndApplyStatus(worktime, ApplyStatus.FIX);
	}

	public SortedMap<LocalDate, List<Apply>> findFixedPersonalApplies(
			SortedMap<LocalDate, List<DetailWorktime>> monthlyDetailWorktimes, User user) {
		SortedMap<LocalDate, List<Apply>> monthlyApplies = new TreeMap<>();
		for (LocalDate date : monthlyDetailWorktimes.keySet()) {
			List<Apply> applies = readOnlyService.findByUserAndDateAndStatus(user, date, ApplyStatus.FIX);
			if (applies.isEmpty()) {
				continue;
			}
			monthlyApplies.put(date, applies);
		}
		if (monthlyApplies.isEmpty()) {
			throw new CustomException("확정된 스케줄이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		}
		return monthlyApplies;
	}

	public List<SortedMap<Worktime, Apply>> findByUserAndWorktimeAndDay(User user, List<Worktime> weeklyWorktimes) {
		List<SortedMap<Worktime, Apply>> weeklyApplies = new ArrayList<>();
		for (DayOfWeek day : DayOfWeek.values()) {
			SortedMap<Worktime, Apply> dailyApplies = new TreeMap<>((s1, s2) -> s1.getId().compareTo(s2.getId()));
			for (Worktime worktime : weeklyWorktimes) {
				dailyApplies.put(worktime,
						readOnlyService.findByUserAndWorktimeAndDay(user, worktime, day));
			}
			weeklyApplies.add(dailyApplies);
		}
		return weeklyApplies;
	}

	public void updateApplies(User user, List<DetailWorktime> previousDetailWorktimes,
<<<<<<< HEAD
							  List<DetailWorktime> appliedDetailWorktimes) {
=======
		List<DetailWorktime> appliedDetailWorktimes) {
>>>>>>> d256c8f9e163637e57105a885dfdafc4f346b90c
		HashSet<DetailWorktime> previousDetailWorktimeSet = new HashSet<>(previousDetailWorktimes);
		HashSet<DetailWorktime> appliedDetailWorktimeSet = new HashSet<>(appliedDetailWorktimes);

		HashSet<DetailWorktime> intersection = new HashSet<>(previousDetailWorktimes);
		intersection.retainAll(appliedDetailWorktimeSet);

		previousDetailWorktimeSet.removeAll(intersection);
		appliedDetailWorktimeSet.removeAll(intersection);

		List<DetailWorktime> detailWorktimesToDelete = new ArrayList<>(previousDetailWorktimes);
		List<Long> detailWorktimeIds = detailWorktimesToDelete.stream()
				.map(DetailWorktime::getId)
				.collect(Collectors.toList());
		List<Apply> appliesToDelete = readOnlyService.findByUserAndDetailWorktimeIds(user, detailWorktimeIds);
		log.info("사용자 ID: {}, 상세 근무 시간 ID: {}에 따른 신청 정보를 삭제합니다.", user.getId(), detailWorktimeIds);
		writeOnlyService.deleteAll(appliesToDelete);

		List<DetailWorktime> appliesToCreate = new ArrayList<>(appliedDetailWorktimeSet);
		log.info("사용자 ID: {}에 대하여 신청 정보를 생성합니다.", user.getId());
		writeOnlyService.createApplies(user, appliesToCreate);
	}
}