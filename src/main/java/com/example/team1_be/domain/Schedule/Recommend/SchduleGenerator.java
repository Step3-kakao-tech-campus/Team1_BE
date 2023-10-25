package com.example.team1_be.domain.Schedule.Recommend;

import com.example.team1_be.domain.Apply.Apply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchduleGenerator {
    private List<Apply> applyList;
    private Map<Long, Integer> requestMap;
    private List<List<Apply>> generatedApplies;
    private final int GEN_LIMIT = 3;
    private int limit;

    public SchduleGenerator(List<Apply> applyList, Map<Long, Integer> requestMap) {
        this.applyList = applyList;
        this.requestMap = requestMap;
        this.generatedApplies = new ArrayList<>();
        this.limit = GEN_LIMIT;
    }

    public List<List<Apply>> generateSchedule() {
        recursiveSearch(this.applyList, 0, this.requestMap, new ArrayList<>());
        return generatedApplies;
    }

    public void recursiveSearch(List<Apply> applyList, int index, Map<Long, Integer> remainRequestMap, List<Apply> fixedApplies) {
        if (remainRequestMap.values().stream().mapToInt(x -> x).sum() == 0 || index == applyList.size()) {
            if (limit != 0) {
                this.generatedApplies.add(new ArrayList<>(fixedApplies));
                limit--;
            }
            return;
        }

        while (index < applyList.size()) {
            Map<Long, Integer> copiedRequestMap = new HashMap<>(remainRequestMap);
            List<Apply> copiedFixedApplies = new ArrayList<>(fixedApplies);

            Apply selectedApply = applyList.get(index);
            Long selectedWorktimeId = selectedApply.getWorktime().getId();
            Integer selectedAppliers = copiedRequestMap.get(selectedWorktimeId);

            if (selectedAppliers != null && selectedAppliers != 0) {
                copiedRequestMap.put(selectedWorktimeId, selectedAppliers - 1);
                copiedFixedApplies.add(selectedApply);
                recursiveSearch(applyList, index + 1, copiedRequestMap, copiedFixedApplies);
                if (limit == 0) {
                    return;
                }
            }

            index++;
        }
    }
}
