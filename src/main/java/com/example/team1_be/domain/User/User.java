package com.example.team1_be.domain.User;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.User.Role.Role;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "users")
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long kakaoId;

	@Size(min = 2, max = 10)
	@Column(nullable = false)
	private String name;

	@Size(min = 13, max = 13)
	private String phoneNumber;

	@NotNull
	private Boolean isAdmin;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<Role> roles;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Apply> applies;

	@Builder
	public User(Long id, Long kakaoId, String name, String phoneNumber, Boolean isAdmin, Set<Role> roles, Group group,
		List<Apply> applies) {
		this.id = id;
		this.kakaoId = kakaoId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.isAdmin = isAdmin;
		this.roles = roles;
		this.group = group;
		this.applies = applies;
	}

	public void updateGroup(Group group) {
		this.group = group;
	}
}
