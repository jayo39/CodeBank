package com.jnjnetwork.CodeBank.repository;

import com.jnjnetwork.CodeBank.domain.Upvote;
import com.jnjnetwork.CodeBank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpvoteRepository extends JpaRepository<Upvote, Long> {
    boolean existsByUserIdAndSnippetId(Long userId, Long snippetId);
    int deleteByUserIdAndSnippetId(Long userId, Long snippetId);
    List<Upvote> findByUser(User user);
}
