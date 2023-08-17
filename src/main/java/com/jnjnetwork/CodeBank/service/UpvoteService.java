package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.domain.Upvote;
import com.jnjnetwork.CodeBank.domain.User;

import java.util.List;

public interface UpvoteService {
    boolean isExist(Long user_id, Long snippet_id);
    int removeUpvote(Long user_id, Long snippet_id);
    void addUpvote(User user, Long snippet_id);
    List<Upvote> getLikedPosts(User user);
}
