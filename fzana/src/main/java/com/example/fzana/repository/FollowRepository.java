package com.example.fzana.repository;

import com.example.fzana.domain.Follow;
import com.example.fzana.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}
