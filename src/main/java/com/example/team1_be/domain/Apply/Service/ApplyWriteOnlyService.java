package com.example.team1_be.domain.Apply.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;
import com.example.team1_be.domain.Apply.ApplyStatus;
import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.User.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplyWriteOnlyService {
	private final ApplyRepository repository;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void deleteAll(List<Apply> appliesToDelete) {
		logger.info("{}개의 신청 정보를 삭제합니다.", appliesToDelete.size());
		repository.deleteAll(appliesToDelete);
	}

	public void createApplies(User user, List<DetailWorktime> appliesToCreate) {
		List<Apply> applies = appliesToCreate.stream()
			.map(detailWorktime -> Apply.builder()
				.status(ApplyStatus.REMAIN)
				.user(user)
				.detailWorktime(detailWorktime)
				.build())
			.collect(
				Collectors.toList());
		logger.info("사용자 ID: {}에 대한 {}개의 신청 정보를 생성합니다.", user.getId(), applies.size());
		createApplies(applies);
	}

	public void createApplies(List<Apply> applies) {
		logger.info("{}개의 신청 정보를 생성합니다.", applies.size());
		repository.saveAll(applies);
	}
}
