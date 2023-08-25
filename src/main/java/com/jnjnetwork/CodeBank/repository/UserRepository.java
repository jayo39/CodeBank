package com.jnjnetwork.CodeBank.repository;

import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.util.U;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
