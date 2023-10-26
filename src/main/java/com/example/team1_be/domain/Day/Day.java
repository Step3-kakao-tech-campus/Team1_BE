package com.example.team1_be.domain.Day;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.example.team1_be.domain.Week.Week;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "days")
public class Day extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Min(1)
	@Max(7)
	private int dayOfWeek;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "week_id")
	@NotNull
	private Week week;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "day")
	private List<Worktime> worktimes;

	@Builder
	public Day(Long id, int dayOfWeek, Week week, List<Worktime> worktimes) {
		this.id = id;
		this.dayOfWeek = dayOfWeek;
		this.week = week;
		this.worktimes = worktimes;
	}
}
