package com.example.concalendar.user.repository;

import com.example.concalendar.user.entity.User;

public interface UserRepositoryCustom {
    public User existsByNickname(String nickname);
}
