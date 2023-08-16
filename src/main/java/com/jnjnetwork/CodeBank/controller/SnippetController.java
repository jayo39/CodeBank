package com.jnjnetwork.CodeBank.controller;

import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.domain.SnippetValidator;
import com.jnjnetwork.CodeBank.service.SnippetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/snippet")
public class SnippetController {
    @Autowired
    SnippetService snippetService;

    @GetMapping("/add")
    public void add() {;}

    @PostMapping("/add")
    public String addOk(@RequestParam("upfile") MultipartFile file, @Valid Snippet snippet, BindingResult result, RedirectAttributes reAttr, Model model) {
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
        int saveResult = snippetService.save(snippet, file);
        return "/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new SnippetValidator());
    }
}
