package com.jnjnetwork.CodeBank.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        String email = user.getEmail();
        if(email == null || email.trim().isEmpty()) {
            errors.rejectValue("email", "noEmail");
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) {
            errors.rejectValue("email", "badEmail");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "noName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "noPass");

        if(!user.getPassword().equals(user.getRe_password())){
            errors.rejectValue("re_password", "noMatch");
        }
    }
}