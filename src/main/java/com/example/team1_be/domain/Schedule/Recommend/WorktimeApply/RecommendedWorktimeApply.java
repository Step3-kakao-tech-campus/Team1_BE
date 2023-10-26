package com.example.team1_be.domain.Schedule.Recommend.WorktimeApply;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule.RecommendedWeeklySchedule;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table
public class RecommendedWorktimeApply extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "apply_id")
	private Apply apply;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recommendedWeeklySchedule_id")
	private RecommendedWeeklySchedule recommendedWeeklySchedule;

	@Builder
	public RecommendedWorktimeApply(Long id, Apply apply, RecommendedWeeklySchedule recommendedWeeklySchedule) {
		this.id = id;
		this.apply = apply;
		this.recommendedWeeklySchedule = recommendedWeeklySchedule;
	}
}
