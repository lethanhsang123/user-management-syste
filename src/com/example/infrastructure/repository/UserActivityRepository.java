package com.example.infrastructure.repository;

import com.example.domain.UserActivity;

import java.util.List;

public interface UserActivityRepository {

    void mappingUserDataSource();
    List<UserActivity> findAll();
    List<UserActivity> findByUserId(int userId);
    void save(UserActivity activity);
}
