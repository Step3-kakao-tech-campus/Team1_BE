package com.example.team1_be.domain.Schedule.Recommend;

import com.example.team1_be.domain.Apply.Apply;
import org.apache.el.util.ReflectionUtil;

import java.util.*;

public class SchduleGenerator {
    private List<Apply> applyList;
    private Map<Long, Integer> requestMap;
    private List<List<Apply>> generatedApplies;
    private int limit;

    public SchduleGenerator(List<Apply> applyList, Map<Long, Integer> requestMap) {
        this.applyList = applyList;
        this.requestMap = requestMap;
        this.generatedApplies = new ArrayList<>();
    }

    public List<List<Apply>> generateSchedule(int limit) {
        this.limit = limit;
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
