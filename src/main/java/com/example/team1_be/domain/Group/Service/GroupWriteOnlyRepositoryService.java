package com.example.team1_be.domain.Group.Service;

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

	public void creatGroup(Group group) {
		repository.save(group);
	}
}
