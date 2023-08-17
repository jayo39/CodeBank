package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Snippet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SnippetService {
    void save(Snippet snippet);
    int save(Snippet snippet, MultipartFile file);
    List<Snippet> findAll();
    List<Snippet> findPublic();
    Snippet findById(Long id);
}
