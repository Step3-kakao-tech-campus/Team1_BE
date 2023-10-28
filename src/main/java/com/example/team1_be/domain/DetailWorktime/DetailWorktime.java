package com.example.team1_be.domain.DetailWorktime;

import java.time.DayOfWeek;
import java.time.LocalDate;
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

import com.example.team1_be.domain.Apply.Apply;
import com.example.team1_be.domain.Worktime.Worktime;
import com.example.team1_be.utils.audit.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table
public class DetailWorktime extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private LocalDate date;

	@NotNull
	private DayOfWeek dayOfWeek;

	@NotNull
	private Long amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "worktime_id")
	private Worktime worktime;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detailWorktime")
	private List<Apply> applies;

	@Builder
	public DetailWorktime(Long id, LocalDate date, DayOfWeek dayOfWeek, Long amount, Worktime worktime,
		List<Apply> applies) {
		this.id = id;
		this.date = date;
		this.dayOfWeek = dayOfWeek;
		this.amount = amount;
		this.worktime = worktime;
		this.applies = applies;
	}
}
