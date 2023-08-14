package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.User;

public interface UserService {
    User findByEmail(String username);
    boolean isExist(String email);
    int register(User user);
    long countUserTotal();
}
