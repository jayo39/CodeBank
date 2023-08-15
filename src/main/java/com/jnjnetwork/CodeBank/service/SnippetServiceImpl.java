package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.repository.SnippetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SnippetServiceImpl implements SnippetService{
    private SnippetRepository snippetRepository;

    @Autowired
    public void setSnippetRepository(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    @Override
    public int save(Snippet snippet) {
        // TODO
        return 0;
    }

    @Override
    public int save(Snippet snippet, MultipartFile file) {
        try {
            snippetRepository.save(snippet);
            return 1;
        } catch (Exception e){
            return 0;
        }
    }
}
