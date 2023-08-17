package com.jnjnetwork.CodeBank.controller;

import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.domain.SnippetValidator;
import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.service.SnippetService;
import com.jnjnetwork.CodeBank.service.UpvoteService;
import com.jnjnetwork.CodeBank.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/snippet")
public class SnippetController {
    @Autowired
    SnippetService snippetService;
    @Autowired
    UpvoteService upvoteService;

    @GetMapping("/add")
    public void add() {;}

    @PostMapping("/add")
    public String addOk(@RequestParam("upfile") MultipartFile file
            , @RequestParam(value = "isEnabled", required = false) String isEnabled
            , @Valid Snippet snippet
            , BindingResult result
            , RedirectAttributes reAttr
            , Model model) {
        if (result.hasErrors()) {
            reAttr.addFlashAttribute("title", snippet.getTitle());
            reAttr.addFlashAttribute("language", snippet.getLanguage());
            reAttr.addFlashAttribute("code", snippet.getCode());
            reAttr.addFlashAttribute("description", snippet.getDescription());
            reAttr.addFlashAttribute("img", snippet.getImg());
            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList) {
                System.out.println(err.getField() + " : " + err.getCode());
                reAttr.addFlashAttribute("error_" + err.getField(), err.getCode());
            }
            return "redirect:/snippet/add";
        }
        if (snippet.getTitle() == null || snippet.getTitle().length() == 0) {
            snippet.setTitle("Untitled");
        }
        snippet.setIsPublic("on".equals(isEnabled));
        int saveResult = snippetService.save(snippet, file);
        return "redirect:/list";
    }

    @PostMapping("/upvote")
    @Transactional
    @ResponseBody
    public Map<String, Object> upvoteOk(@RequestParam("snippet_id") Long snippet_id) {
        User user = U.getLoggedUser();
        Snippet snippet = snippetService.findById(snippet_id);
        Map<String, Object> response = new HashMap<>();
        if (upvoteService.isExist(user.getId(), snippet_id)) {
            upvoteService.removeUpvote(user.getId(), snippet_id);
            snippet.setLikeCount(snippet.getLikeCount() - 1);
            snippetService.save(snippet);
            response.put("isUpvote", false);
        } else {
            upvoteService.addUpvote(user, snippet_id);
            snippet.setLikeCount(snippet.getLikeCount() + 1);
            snippetService.save(snippet);
            response.put("isUpvote", true);
        }
        response.put("likeCount", snippet.getLikeCount());
        return response;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new SnippetValidator());
    }
}
