package com.example.fzana.repository;

import com.example.fzana.domain.Follow;
import com.example.fzana.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 특정 팔로워와 팔로잉 관계가 존재하는지 확인
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    // 특정 팔로워와 팔로잉 관계를 조회
    Optional<Follow> findByFollowerAndFollowing(Member follower, Member following);

    Optional<Follow> findByFollowingAndFollower(Member following, Member follower);


    // 특정 멤버를 팔로잉하는 모든 팔로워를 조회
    List<Follow> findByFollower(Member follower);

    // 특정 멤버를 팔로우하는 모든 팔로잉을 조회
    List<Follow> findByFollowing(Member following);
}