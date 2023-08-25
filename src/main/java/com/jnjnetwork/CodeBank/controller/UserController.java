package com.jnjnetwork.CodeBank.controller;

import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.domain.UserValidator;
import com.jnjnetwork.CodeBank.service.SnippetService;
import com.jnjnetwork.CodeBank.service.UserService;
import com.jnjnetwork.CodeBank.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
    @Autowired
    SnippetService snippetService;

    @PostMapping("/loginError")
    public String loginError() {
        return "user/login";
    }

    @RequestMapping("/rejectAuth")
    public void rejectAuth() {;}

    @GetMapping("/login")
    public void login() {;}

    @GetMapping("/profile")
    public void profile(Integer page, Model model) {
        User user = U.getLoggedUser();
        User profileUser = userService.findById(user.getId());
        int followerNum = profileUser.getFollowers().size();
        int followingNum = profileUser.getFollowing().size();
        model.addAttribute("followerNum", followingNum);
        model.addAttribute("followingNum", followingNum);
        model.addAttribute("user", user);
        model.addAttribute("snippets", snippetService.findByUserId(user.getId(), page, model));
    }

    @PostMapping("/register")
    @ResponseBody
    @Transactional
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

    @GetMapping("/followers/{id}")
    public String followers(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        List<User> followers = user.getFollowers();
        List<User> following = user.getFollowing();
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);
        return "user/followers";
    }

    @PostMapping("/followOk")
    @Transactional
    @ResponseBody
    public ResponseEntity<String> followOk(@RequestParam("logged_userId") Long logged_id, @RequestParam("post_userId") Long post_userId) {
        User follower = userService.findById(logged_id);
        User followee = userService.findById(post_userId);

        if(followee.getFollowers().contains(follower)) {
            followee.getFollowers().remove(follower);
            follower.getFollowing().remove(followee);
        } else {
            followee.getFollowers().add(follower);
            follower.getFollowing().add(followee);
        }

        userService.save(follower);
        userService.save(followee);
        return ResponseEntity.ok("Followed!");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserValidator());
    }

}
