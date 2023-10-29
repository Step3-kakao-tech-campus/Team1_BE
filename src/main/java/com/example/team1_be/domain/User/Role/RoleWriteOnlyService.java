package com.example.team1_be.domain.User.Role;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleWriteOnlyService {
	private final RoleRepository repository;

}
