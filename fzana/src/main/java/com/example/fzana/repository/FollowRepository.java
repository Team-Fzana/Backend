package com.example.fzana.repository;

import com.example.fzana.domain.Follow;
import com.example.fzana.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(Member follower, Member following);
    List<Follow> findByFollower(Member follower);
    List<Follow> findByFollowing(Member following);
}