package com.jnjnetwork.CodeBank.repository;

import com.jnjnetwork.CodeBank.domain.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserId(Long user_id, Sort sort);
}
