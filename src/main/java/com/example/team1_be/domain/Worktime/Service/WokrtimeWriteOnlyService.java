package com.example.team1_be.domain.Worktime.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WokrtimeWriteOnlyService {
	private final WorktimeRepository worktimeRepository;

	public List<Worktime> createWorktimes(List<Worktime> worktimes) {
		worktimes = worktimeRepository.saveAll(worktimes);
		return worktimeRepository.findAllById(worktimes.stream().map(Worktime::getId).collect(Collectors.toList()));
	}
}
