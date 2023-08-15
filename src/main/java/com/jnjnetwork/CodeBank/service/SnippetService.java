package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Snippet;
import org.springframework.web.multipart.MultipartFile;

public interface SnippetService {
    int save(Snippet snippet);
    int save(Snippet snippet, MultipartFile file);
}
