package com.test.domain;

public class UserActivity {
    private final int userId;
    private final ActionType actionType;
    private final long timestamp;

    public UserActivity(int userId, ActionType actionType, long timestamp) {
        this.userId = userId;
        this.actionType = actionType;
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getPoints() {
        return actionType.getPoints();
    }

    @Override
    public String toString() {
        return "UserActivity{" +
                "userId=" + userId +
                ", actionType=" + actionType +
                ", timestamp=" + timestamp +
                '}';
    }
}

