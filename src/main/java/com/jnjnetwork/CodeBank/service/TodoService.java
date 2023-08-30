package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Todo;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TodoService {
    void add(Todo todo);
    void deleteById(Long id);
    List<Todo> findTodoById(Long user_id);
    long countTodoTotal();
}
