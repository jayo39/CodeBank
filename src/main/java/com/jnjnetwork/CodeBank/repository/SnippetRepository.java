package com.jnjnetwork.CodeBank.repository;

import com.jnjnetwork.CodeBank.domain.Snippet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    List<Snippet> findByLanguage(String language);
    Page<Snippet> findByUserId(Long id, PageRequest pageRequest);
    Page<Snippet> findByIsPublic(Boolean isPublic, PageRequest pageRequest);
    long countByIsPublic(Boolean isPublic);
}
