package com.jnjnetwork.CodeBank.controller;

import com.jnjnetwork.CodeBank.domain.Todo;
import com.jnjnetwork.CodeBank.domain.TodoValidator;
import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.service.TodoService;
import com.jnjnetwork.CodeBank.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping("/list")
    public List<Todo> list(@RequestParam("user_id") Long user_id) {
        return todoService.findTodoById(user_id);
    }

    @PostMapping("/add")
    @Transactional
    public Map<String, Object> addOk(@Valid Todo todo, BindingResult result) {
        User user = U.getLoggedUser();
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()) {
            response.put("status", "fail");
            return response;
        }
        response.put("status", "success");
        todo.setUser(user);
        todoService.add(todo);
        return response;
    }

    @PostMapping("/delete")
    @Transactional
    public void deleteOk(Long id) {
        todoService.deleteById(id);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new TodoValidator());
    }
}
