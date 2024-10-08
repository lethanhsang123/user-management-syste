package com.example.application;

import com.example.domain.UserActivity;
import com.example.infrastructure.repository.UserActivityRepository;

import java.util.List;

public class CheckUserHistory {

    private final UserActivityRepository repository;

    public CheckUserHistory(UserActivityRepository repository) {
        this.repository = repository;
    }

    public List<UserActivity> execute(int userId, Long startTime, Long endTime) {
        return repository.findByUserId(userId).stream()
                .filter(activity -> (startTime == null || activity.getTimestamp() >= startTime) &&
                        (endTime == null || activity.getTimestamp() <= endTime))
                .toList();
    }

}
