package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Snippet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SnippetService {
    int save(Snippet snippet, MultipartFile file);
    List<Snippet> findAll();
    List<Snippet> findPublic();
}
