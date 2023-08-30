package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Role;
import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User findByEmail(String username);
    boolean isExist(String email);
    int register(User user);
    long countUserTotal();
    List<Role> selectRolesById(Long id);
    User findById(Long id);
    void save(User user);
    int save(User user, MultipartFile file);
}
