package com.jnjnetwork.CodeBank.controller;

import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.domain.UserValidator;
import com.jnjnetwork.CodeBank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/loginError")
    public String loginError() {
        return "user/login";
    }

    @RequestMapping("/rejectAuth")
    public void rejectAuth() {;}

    @GetMapping("/login")
    public void login() {;}

    @GetMapping("/profile")
    public void profile() {;}

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerOk(@Valid User user, BindingResult result) {
        if (userService.isExist(user.getEmail())) {
            return ResponseEntity.badRequest().body("This email already exists.");
        }

        if (result.hasErrors()) {
            FieldError error = result.getFieldErrors().get(0);
            if (error.getCode().equals("noEmail")) {
                return ResponseEntity.badRequest().body("Email is required.");
            } else if (error.getCode().equals("badEmail")) {
                return ResponseEntity.badRequest().body("Invalid email address.");
            } else if (error.getCode().equals("noName")) {
                return ResponseEntity.badRequest().body("Name is required.");
            } else if (error.getCode().equals("noPass")) {
                return ResponseEntity.badRequest().body("Password is required.");
            } else if (error.getCode().equals("noMatch")) {
                return ResponseEntity.badRequest().body("Passwords do not match.");
            }
        }
        userService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserValidator());
    }
}
