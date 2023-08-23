package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.domain.User;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SnippetService {
    void save(Snippet snippet);
    int save(Snippet snippet, MultipartFile file);
    void deleteById(Long id);
    List<Snippet> findAll();
    List<Snippet> findPublic(String sort, Integer page, Model model);
    List<Snippet> findByUserId(Long id, Integer page, Model model);
    Snippet findById(Long id);
    long countPublicSnippets(Boolean isPublic);
}
