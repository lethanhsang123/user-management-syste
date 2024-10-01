package com.test.application;

import com.test.domain.UserActivity;
import com.test.infrastructure.repository.UserActivityRepository;

public class CalculateUserPoints {

    private final UserActivityRepository repository;

    public CalculateUserPoints(UserActivityRepository repository) {
        this.repository = repository;
    }

    public int execute(int userId, Long startTime, Long endTime) {
        return repository.findByUserId(userId).stream()
                .filter(activity -> (startTime == null || activity.getTimestamp() >= startTime) &&
                        (endTime == null || activity.getTimestamp() <= endTime))
                .mapToInt(UserActivity::getPoints).sum();
    }

}
