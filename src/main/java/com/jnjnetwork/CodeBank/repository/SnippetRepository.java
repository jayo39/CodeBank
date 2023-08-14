package com.jnjnetwork.CodeBank.repository;

import com.jnjnetwork.CodeBank.domain.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {
    // Find snippets by category
    List<Snippet> findByLanguage(String language);
}
