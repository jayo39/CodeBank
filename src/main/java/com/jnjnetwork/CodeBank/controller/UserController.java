package com.jnjnetwork.CodeBank.controller;

import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.domain.UserValidator;
import com.jnjnetwork.CodeBank.service.SnippetService;
import com.jnjnetwork.CodeBank.service.UserService;
import com.jnjnetwork.CodeBank.util.U;
import jakarta.persistence.EntityManager;
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
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    EntityManager entityManager;
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
        User loggedUser = U.getLoggedUser();
        User user = userService.findById(loggedUser.getId());
        User profileUser = userService.findById(user.getId());
        int followerNum = profileUser.getFollowers().size();
        int followingNum = profileUser.getFollowing().size();
        model.addAttribute("followerNum", followerNum);
        model.addAttribute("followingNum", followingNum);
        model.addAttribute("user", user);
        model.addAttribute("snippets", snippetService.findByUserId(user.getId(), false, page, model));
    }

    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable Long id, Integer page, Model model) {
        User logged_user = U.getLoggedUser();
        User user = userService.findById(id);
        int followerNum = user.getFollowers().size();
        int followingNum = user.getFollowing().size();
        List<Long> followerIdList = new ArrayList<>();
        if(logged_user != null && logged_user.getId() == user.getId()) {
            return "redirect:/user/profile";
        }
        for(var u : user.getFollowers()) {
            followerIdList.add(u.getId());
        }
        model.addAttribute("followerIdList", followerIdList);
        model.addAttribute("followerNum", followerNum);
        model.addAttribute("followingNum", followingNum);
        model.addAttribute("user", user);
        model.addAttribute("snippets", snippetService.findByUserId(user.getId(), true, page, model));
        return "user/view";
    }

    @PostMapping("/save")
    @Transactional
    public String saveOk(@RequestParam("upfile") MultipartFile file, @RequestParam("user_id") Long user_id) {
        User user = userService.findById(user_id);
        userService.save(user, file);
        return "redirect:/user/profile";
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
    public String followers(@RequestParam(name = "list", required = false) String listType, @PathVariable Long id, Model model) {
        User user = userService.findById(id);
        List<User> followers = user.getFollowers();
        List<User> following = user.getFollowing();
        if(listType == null || listType.isEmpty()) {
            listType = "following";
        }
        model.addAttribute("user", user);
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);
        model.addAttribute("list", listType);
        return "user/followers";
    }

    @PostMapping("/followOk")
    @Transactional
    @ResponseBody
    public Map<String, Object> followOk(@RequestParam("logged_userId") Long logged_id, @RequestParam("post_userId") Long post_userId) {
        User follower = userService.findById(logged_id);
        User followee = userService.findById(post_userId);
        Map<String, Object> response = new HashMap<>();

        if(followee.getFollowers().contains(follower)) {
            followee.getFollowers().remove(follower);
            follower.getFollowing().remove(followee);
            response.put("followStatus", "unfollow");
        } else {
            followee.getFollowers().add(follower);
            follower.getFollowing().add(followee);
            response.put("followStatus", "follow");
        }

        userService.save(follower);
        userService.save(followee);
        entityManager.flush();
        entityManager.clear();
        response.put("followerCount", userService.findById(post_userId).getFollowers().size());
        return response;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserValidator());
    }

}
