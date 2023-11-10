package com.example.team1_be.domain.Worktime.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.domain.Worktime.WorktimeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WokrtimeWriteOnlyService {
	private final WorktimeRepository worktimeRepository;

	public List<Worktime> createWorktimes(List<Worktime> worktimes) {
		log.info("근무 시간을 생성합니다.");
		worktimes = worktimeRepository.saveAll(worktimes);
		List<Worktime> savedWorktimes = worktimeRepository.findAllById(
			worktimes.stream().map(Worktime::getId).collect(Collectors.toList()));
		log.info("근무 시간 생성이 완료되었습니다.");
		return savedWorktimes;
	}
}
