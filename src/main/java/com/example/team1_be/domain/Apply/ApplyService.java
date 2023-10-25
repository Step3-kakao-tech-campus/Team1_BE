package com.example.team1_be.domain.Apply;

import com.example.team1_be.domain.User.User;
import com.example.team1_be.domain.Worktime.Worktime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyService {
    private final ApplyRepository applyRepository;

    public List<Apply> findAppliesByWorktimes(List<Worktime> worktimes) {
        List<Long> worktimeIds = worktimes.stream()
                .map(Worktime::getId)
                .collect(Collectors.toList());

        return applyRepository.findAppliesByWorktimeIds(worktimeIds);
    }

    public List<Apply> findAppliesByWorktime(Worktime worktime) {
        return applyRepository.findAppliesByWorktimeId(worktime.getId());
    }

    public List<Worktime> findWorktimesByYearMonthAndStatusAndUser(LocalDate fromDate, LocalDate toDate, User user, ApplyStatus status) {
        return applyRepository.findByYearMonthAndStatusAndMemberId(fromDate, toDate, user.getId(), status);
    }

    @Transactional
    public void createApplies(List<Apply> applies) {
        applyRepository.saveAll(applies);
    }

    public List<Apply> findFixedAppliesByWorktime(Worktime worktime) {
        return applyRepository.findFixedAppliesByWorktimeId(worktime.getId());
    }
}
