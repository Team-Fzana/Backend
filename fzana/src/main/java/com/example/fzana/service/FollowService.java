package com.example.fzana.service;

import com.example.fzana.domain.Follow;
import com.example.fzana.domain.User;
import com.example.fzana.dto.FollowForm;
import com.example.fzana.repository.FollowRepository;
import com.example.fzana.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        User targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new IllegalArgumentException("대상 사용자를 찾을 수 없습니다"));

        if (userId.equals(targetUserId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(user, targetUser);
        if (existingFollow.isPresent()) {
            throw new IllegalStateException("이미 팔로우하고 있습니다.");
        }

        Follow follow = new Follow();
        follow.setFollower(user);
        follow.setFollowing(targetUser);
        followRepository.save(follow);
        return "사용자가 성공적으로 팔로우 목록에 추가되었습니다.";
    }

    // 서비스 계층에 정의된 getFollowingList 메소드
    public List<Follow> getFollowingList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        return followRepository.findByFollower(user);
    }
}
