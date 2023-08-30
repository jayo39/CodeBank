package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Todo;
import com.jnjnetwork.CodeBank.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{
    TodoRepository todoRepository;

    @Autowired
    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void add(Todo todo) {
        todoRepository.saveAndFlush(todo);
    }

    @Override
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public List<Todo> findTodoById(Long user_id) {
        Sort sort = Sort.by(Sort.Order.desc("regDate"));
        return todoRepository.findByUserId(user_id, sort);
    }

    @Override
    public long countTodoTotal() {
        return todoRepository.count();
    }
}
