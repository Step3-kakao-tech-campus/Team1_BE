package com.example.team1_be.domain.Group;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.team1_be.domain.Group.Invite.Invite;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "groups")
public class Group extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 2, max = 50)
	@NotNull
	private String name;

	@Size(min = 13, max = 13)
	private String phoneNumber;

	@Column(unique = true)
	private String businessNumber;

	@Size(max = 100)
	@NotNull
	private String address;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	private List<Week> weeks;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	private List<User> users;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "group")
	private Invite invite;

	@Builder
	public Group(Long id, String name, String phoneNumber, String businessNumber, String address, List<Week> weeks,
		List<User> users, Invite invite) {
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.businessNumber = businessNumber;
		this.address = address;
		this.weeks = weeks;
		this.users = users;
		this.invite = invite;
	}
}