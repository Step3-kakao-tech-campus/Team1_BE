package com.example.team1_be.domain.Worktime;

import java.time.LocalTime;
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
import javax.validation.constraints.NotNull;

import com.example.team1_be.domain.DetailWorktime.DetailWorktime;
import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class Worktime extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String title;

	@NotNull
	private LocalTime startTime;

	@NotNull
	private LocalTime endTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "week_id")
	private Week week;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "worktime")
	private List<DetailWorktime> days;

	@Builder
	public Worktime(Long id, String title, LocalTime startTime, LocalTime endTime, Week week,
		List<DetailWorktime> days) {
		this.id = id;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.week = week;
		this.days = days;
	}

	public void updateWeek(Week week) {
		this.week = week;
	}
}
