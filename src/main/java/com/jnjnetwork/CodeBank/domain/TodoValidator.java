package com.jnjnetwork.CodeBank.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class TodoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Todo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Todo todo = (Todo) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "* Write a todo.");
    }
}
