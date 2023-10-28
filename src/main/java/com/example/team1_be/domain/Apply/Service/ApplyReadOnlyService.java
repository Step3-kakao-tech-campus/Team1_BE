package com.example.team1_be.domain.Apply.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Worktime.Worktime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyReadOnlyService {
	private final ApplyRepository repository;

	public List<Apply> findAppliesByWorktimes(List<Long> worktimeIds) {
		return repository.findAppliesByWorktimeIds(worktimeIds);
	}

	public List<Apply> findByUserAndDateAndStatus(User user, LocalDate date, ApplyStatus status) {
		return repository.findByUserAndDateAndStatus(user.getId(), date, status);
	}

	public List<User> findUsersByWorktimeAndApplyStatus(DetailWorktime worktime, ApplyStatus status) {
		return repository.findUsersByWorktimeAndApplyStatus(worktime.getId(), status);
	}

	public Apply findByUserAndWorktimeAndDay(User user, Worktime worktime, DayOfWeek day) {
		return repository.findByUserAndWorktimeAndDay(user.getId(), worktime.getId(), day).orElse(null);
	}

	public List<Apply> findByUserAndDetailWorktimeIds(User user, List<Long> detailWorktimeIds) {
		return repository.findByUserAndDetailWorktimeIds(user.getId(), detailWorktimeIds);
	}
}
