package com.example.application;

import com.example.domain.UserActivity;
import com.example.infrastructure.repository.UserActivityRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateLeaderboard {

    private final UserActivityRepository repository;

    public GenerateLeaderboard(UserActivityRepository repository) {
        this.repository = repository;
    }

    public List<Map.Entry<Integer, Integer>> execute(Long startTime, Long endTime) {
        Map<Integer, Integer> userPoints = new HashMap<>();

        for (UserActivity activity : repository.findAll()) {
            int userId = activity.getUserId();
            int points = activity.getPoints();
            userPoints.put(userId, userPoints.getOrDefault(userId, 0) + points);
        }

        return userPoints.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .toList();
    }

}
