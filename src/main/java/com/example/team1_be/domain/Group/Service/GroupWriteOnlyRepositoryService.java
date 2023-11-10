package com.example.team1_be.domain.Group.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Group.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GroupWriteOnlyRepositoryService {
	private final GroupRepository repository;

	public void creatGroup(Group group) {
		log.info("그룹 저장됨");
		repository.save(group);
	}
}
