package com.example.team1_be.domain.Schedule.Recommend.WorktimeApply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendedWorktimeApplyService {
    private final RecommendedWorktimeApplyRepository recommendedWorktimeApplyRepository;

    @Transactional
    public void createRecommendedWorktimeApplies(List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
        recommendedWorktimeApplyRepository.saveAll(recommendedWorktimeApplies);
    }

    @Transactional
    public void deleteAll(List<RecommendedWorktimeApply> recommendedWorktimeApplies) {
        recommendedWorktimeApplyRepository.deleteAll(recommendedWorktimeApplies);
    }
}
