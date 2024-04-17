package com.example.fzana.service;

import com.example.fzana.domain.Follow;
import com.example.fzana.domain.User;
import com.example.fzana.dto.FollowForm;
import com.example.fzana.repository.FollowRepository;
import com.example.fzana.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    public String createFollow(FollowForm followForm) {
        Long userId = followForm.getUserId();
        Long targetUserId = followForm.getTargetUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        User targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        if (userId.equals(targetUserId)) {
            throw new IllegalArgumentException("You cannot follow yourself.");
        }

        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(user, targetUser);
        if (existingFollow.isPresent()) {
            throw new IllegalStateException("Already following.");
        }

        Follow follow = new Follow();
        follow.setFollower(user);
        follow.setFollowing(targetUser);
        followRepository.save(follow);
        return "User has been successfully added to following.";
    }
}
