package com.example.team1_be.domain.Group.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupWriteOnlyRepositoryService {
	private final GroupRepository repository;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void creatGroup(Group group) {
		logger.info("그룹 저장됨");
		repository.save(group);
	}
}
