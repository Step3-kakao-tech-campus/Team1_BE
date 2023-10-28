package com.example.team1_be.domain.Apply;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.errors.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyService {
	private final ApplyRepository repository;

	public List<Apply> findAppliesByWorktimes(List<Worktime> worktimes) {
		List<Long> detailWorktimeIds = worktimes.stream()
			.map(Worktime::getId)
			.collect(Collectors.toList());

		return repository.findAppliesByWorktimeIds(detailWorktimeIds);
	}

	@Transactional
	public void createApplies(List<Apply> applies) {
		repository.saveAll(applies);
	}

	public SortedMap<LocalDate, List<Apply>> findFixedApplies(
		SortedMap<LocalDate, List<DetailWorktime>> monthlyDetailWorktimes, User user) {
		SortedMap<LocalDate, List<Apply>> monthlyApplies = new TreeMap<>((s1, s2) -> s1.compareTo(s2));
		for (LocalDate date : monthlyDetailWorktimes.keySet()) {
			System.out.println("date is : " + date);
			List<Apply> applies = repository.findByUserAndDateAndStatus(user.getId(), date, ApplyStatus.FIX);
			if (applies.isEmpty()) {
				continue;
			}
			monthlyApplies.put(date, applies);
		}

		if (monthlyApplies.isEmpty()) {
			throw new NotFoundException("확정된 스케줄이 없습니다.");
		}
		return monthlyApplies;
	}

	public List<User> findUsersByWorktimeAndApplyStatus(DetailWorktime worktime, ApplyStatus status) {
		List<User> users = repository.findUsersByWorktimeAndApplyStatus(worktime.getId(), status);
		if (users.isEmpty()) {
			throw new NotFoundException("확정된 신청자를 찾을 수 없습니다.");
		}
		return users;
	}

	public List<User> findUsersByWorktimeAndFixedApplies(DetailWorktime worktime) {
		return findUsersByWorktimeAndApplyStatus(worktime, ApplyStatus.FIX);
	}

	public SortedMap<LocalDate, List<Apply>> findFixedPersonalApplies(
		SortedMap<LocalDate, List<DetailWorktime>> monthlyDetailWorktimes, User user) {
		SortedMap<LocalDate, List<Apply>> monthlyApplies = new TreeMap<>();
		for (LocalDate date : monthlyDetailWorktimes.keySet()) {
			List<Apply> applies = repository.findByUserAndDateAndStatus(user.getId(), date, ApplyStatus.FIX);
			if (applies.isEmpty()) {
				continue;
			}
			monthlyApplies.put(date, applies);
		}
		if (monthlyApplies.isEmpty()) {
			throw new NotFoundException("확정된 스케줄이 존재하지 않습니다.");
		}
		return monthlyApplies;
	}
}
