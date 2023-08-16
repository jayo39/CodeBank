package com.jnjnetwork.CodeBank.controller;

import com.jnjnetwork.CodeBank.service.SnippetService;
import com.jnjnetwork.CodeBank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    UserService userService;
    @Autowired
    SnippetService snippetService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("userTotal", userService.countUserTotal());
        return "/index.html";
    }

    @GetMapping("/list")
    public void list(Model model) {
        model.addAttribute("snippets", snippetService.findAll());
    }
}
