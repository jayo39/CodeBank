package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.domain.Upvote;
import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.repository.SnippetRepository;
import com.jnjnetwork.CodeBank.repository.UpvoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpvoteServiceImpl implements UpvoteService {
    private UpvoteRepository upvoteRepository;
    private SnippetRepository snippetRepository;

    @Autowired
    public void setSnippetRepository(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    @Autowired
    public void setUpvoteRepository(UpvoteRepository upvoteRepository) {
        this.upvoteRepository = upvoteRepository;
    }


    @Override
    public boolean isExist(Long user_id, Long snippet_id) {
        return upvoteRepository.existsByUserIdAndSnippetId(user_id, snippet_id);
    }

    @Override
    public int removeUpvote(Long user_id, Long snippet_id) {
        return upvoteRepository.deleteByUserIdAndSnippetId(user_id, snippet_id);
    }

    @Override
    public void addUpvote(User user, Long snippet_id) {
        Upvote upvote = Upvote.builder()
                .user(user)
                .snippet(snippetRepository.findById(snippet_id).orElseThrow(RuntimeException::new))
                .build();
        upvoteRepository.save(upvote);
    }

    @Override
    public List<Upvote> getLikedPosts(User user) {
        return upvoteRepository.findByUser(user);
    }
}
