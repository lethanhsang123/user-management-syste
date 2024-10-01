package com.example.domain;

public enum ActionType {
    LIKE(1),
    CLICK(2),
    SHARE(3),
    COMMENT(4),
    PURCHASE(5);

    private final int points;

    ActionType(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
