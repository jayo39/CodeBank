package com.jnjnetwork.CodeBank.controller;

import com.jnjnetwork.CodeBank.domain.Upvote;
import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.service.SnippetService;
import com.jnjnetwork.CodeBank.service.TodoService;
import com.jnjnetwork.CodeBank.service.UpvoteService;
import com.jnjnetwork.CodeBank.service.UserService;
import com.jnjnetwork.CodeBank.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

@Controller
public class IndexController {
    @Autowired
    UserService userService;
    @Autowired
    SnippetService snippetService;
    @Autowired
    UpvoteService upvoteService;
    @Autowired
    TodoService todoService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("userTotal", userService.countUserTotal());
        model.addAttribute("todoTotal", todoService.countTodoTotal());
        model.addAttribute("snippetTotal", snippetService.countPublicSnippets(true));
        return "/index";
    }

    @GetMapping("/list")
    public void list(@ModelAttribute("sort") String sortMethod, Integer page, Model model) {
        User user = U.getLoggedUser();
        List<Long> list = new ArrayList<>();
        List<Long> following_userId = new ArrayList<>();
        List<Upvote> likedPosts = upvoteService.getLikedPosts(user);
        if (user != null ) {
            List<User> following_users = userService.findById(user.getId()).getFollowing();
            for(User following : following_users) {
                following_userId.add(following.getId());
            }
        }
        for(Upvote post : likedPosts) {
            list.add(post.getSnippet().getId());
        }
        model.addAttribute("snippets", snippetService.findPublic(following_userId, sortMethod, page, model));
        model.addAttribute("sort", sortMethod);
        model.addAttribute("user", user);
        model.addAttribute("likedPosts", list);
    }

    @PostMapping("/listSort")
    public String listSort(@RequestParam("sort") String sortMethod, RedirectAttributes attr) {
        attr.addFlashAttribute("sort", sortMethod);
        return "redirect:/list";
    }

    @GetMapping("/users")
    public String users(Integer page, Model model) {
        model.addAttribute("users", userService.findAllUsers(page, model));
        return "/findUser";
    }
}
