package com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApply;
import com.example.team1_be.domain.User.User;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table
public class RecommendedWeeklySchedule extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recommendedWeeklySchedule", orphanRemoval = true)
	private List<RecommendedWorktimeApply> recommendedWorktimeApplies;

	@Builder
	public RecommendedWeeklySchedule(Long id, User user, List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
		this.id = id;
		this.user = user;
		this.recommendedWorktimeApplies = recommendedWorktimeApplies;
	}
}
