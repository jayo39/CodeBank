package com.jnjnetwork.CodeBank.repository;

import com.jnjnetwork.CodeBank.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void initialize() {
        // Make 2 default roles
        Role role_member = Role.builder()
                .name("ROLE_MEMBER")
                .build();
        Role role_admin = Role.builder()
                .name("ROLE_ADMIN")
                .build();
        roleRepository.saveAndFlush(role_member);
        roleRepository.saveAndFlush(role_admin);
    }

}