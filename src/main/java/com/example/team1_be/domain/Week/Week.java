package com.example.team1_be.domain.Week;

import java.time.LocalDate;
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

import com.example.team1_be.domain.Group.Group;
import com.example.team1_be.domain.Schedule.Recommend.WeeklySchedule.RecommendedWeeklySchedule;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Week extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	private WeekRecruitmentStatus status;

	@NotNull
	private LocalDate startDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	@NotNull
	private Group group;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "week")
	private List<Worktime> worktimes;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "week")
	private List<RecommendedWeeklySchedule> recommendedWeeklySchedule;

	@Builder
	public Week(Long id, WeekRecruitmentStatus status, LocalDate startDate, Group group, List<Worktime> worktimes,
		List<RecommendedWeeklySchedule> recommendedWeeklySchedule) {
		this.id = id;
		this.status = status;
		this.startDate = startDate;
		this.group = group;
		this.worktimes = worktimes;
		this.recommendedWeeklySchedule = recommendedWeeklySchedule;
	}

	public Week updateStatus(WeekRecruitmentStatus status) {
		this.status = status;
		return this;
	}
}
