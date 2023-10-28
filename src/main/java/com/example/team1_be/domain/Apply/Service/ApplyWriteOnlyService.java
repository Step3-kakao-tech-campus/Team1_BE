package com.example.team1_be.domain.Apply.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Apply.ApplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplyWriteOnlyService {
	private final ApplyRepository repository;

	public void createApplies(List<Apply> applies) {
		repository.saveAll(applies);
	}
}
