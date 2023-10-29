package com.example.team1_be.domain.Apply;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApply;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Apply extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ApplyStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "detailWorktime_id")
	private DetailWorktime detailWorktime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "apply")
	private List<RecommendedWorktimeApply> recommendedWorktimeApplies;

	@Builder
	public Apply(Long id, ApplyStatus status, DetailWorktime detailWorktime, User user,
		List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
		this.id = id;
		this.status = status;
		this.detailWorktime = detailWorktime;
		this.user = user;
		this.recommendedWorktimeApplies = recommendedWorktimeApplies;
	}

	public Apply updateStatus(ApplyStatus status) {
		this.status = status;
		return this;
	}
}
