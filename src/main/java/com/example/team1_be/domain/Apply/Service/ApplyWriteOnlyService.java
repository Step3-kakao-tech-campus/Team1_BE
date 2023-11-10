package com.example.team1_be.domain.Apply.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.User.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ApplyWriteOnlyService {
	private final ApplyRepository repository;

	public void deleteApplies(List<Apply> appliesToDelete) {
		log.info("{}개의 신청 정보를 삭제합니다.", appliesToDelete.size());
		repository.deleteAll(appliesToDelete);
	}

	public void registerAppliesForUser(User user, List<DetailWorktime> detailWorktimes) {
		List<Apply> applies = createAppliesForDetailWorktimes(user, detailWorktimes);
		log.info("사용자 ID: {}에 대한 {}개의 신청 정보를 생성합니다.", user.getId(), applies.size());
		registerAppliesForUser(applies);
	}

	private List<Apply> createAppliesForDetailWorktimes(User user, List<DetailWorktime> detailWorktimes) {
		return detailWorktimes.stream()
			.map(detailWorktime -> Apply.builder()
				.status(ApplyStatus.REMAIN)
				.user(user)
				.detailWorktime(detailWorktime)
				.build())
			.collect(Collectors.toList());
	}

	public void registerAppliesForUser(List<Apply> applies) {
		log.info("{}개의 신청 정보를 생성합니다.", applies.size());
		repository.saveAll(applies);
	}
}
