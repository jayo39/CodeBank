package com.jnjnetwork.CodeBank.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SnippetValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Snippet.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Snippet snippet = (Snippet) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "language", "* Please select the programming language.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "* Write or copy paste a code below.");
    }
}
