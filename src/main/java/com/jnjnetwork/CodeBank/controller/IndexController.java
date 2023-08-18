package com.jnjnetwork.CodeBank.controller;

import com.jnjnetwork.CodeBank.domain.Upvote;
import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.service.SnippetService;
import com.jnjnetwork.CodeBank.service.UpvoteService;
import com.jnjnetwork.CodeBank.service.UserService;
import com.jnjnetwork.CodeBank.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserService userService;
    @Autowired
    SnippetService snippetService;
    @Autowired
    UpvoteService upvoteService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("userTotal", userService.countUserTotal());
        return "/index.html";
    }

    @GetMapping("/list")
    public void list(Integer page, Model model) {
        User user = U.getLoggedUser();
        List<Long> list = new ArrayList<>();
        List<Upvote> likedPosts = upvoteService.getLikedPosts(user);
        for(Upvote post : likedPosts) {
            list.add(post.getSnippet().getId());
        }
        model.addAttribute("user", user);
        model.addAttribute("likedPosts", list);
        model.addAttribute("snippets_new", snippetService.findNewPublic());
        model.addAttribute("snippets", snippetService.findPublic(page, model));
    }
}
