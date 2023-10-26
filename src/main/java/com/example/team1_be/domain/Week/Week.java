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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.team1_be.domain.Day.Day;
import com.example.team1_be.domain.Schedule.Schedule;
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
	@NotNull
	private Schedule schedule;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "week")
	private List<Day> day;

	@Builder
	public Week(Long id, WeekRecruitmentStatus status, LocalDate startDate, Schedule schedule, List<Day> day) {
		this.id = id;
		this.status = status;
		this.startDate = startDate;
		this.schedule = schedule;
		this.day = day;
	}

	public Week updateStatus(WeekRecruitmentStatus status) {
		this.status = status;
		return this;
	}
}
