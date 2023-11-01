package com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.team1_be.domain.Schedule.Recommend.WorktimeApply.RecommendedWorktimeApply;
import com.example.team1_be.domain.Week.Week;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "week_id")
	private Week week;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recommendedWeeklySchedule", orphanRemoval = true)
	private List<RecommendedWorktimeApply> recommendedWorktimeApplies;

	@Builder
	public RecommendedWeeklySchedule(Long id, Week week, List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
		this.id = id;
		this.week = week;
		this.recommendedWorktimeApplies = recommendedWorktimeApplies;
	}
}
