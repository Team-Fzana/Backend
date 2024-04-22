package com.example.fzana.service;

import com.example.fzana.domain.Follow;
import com.example.fzana.domain.Member;
import com.example.fzana.dto.FollowForm;
import com.example.fzana.repository.FollowRepository;
import com.example.fzana.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowRepository followRepository;

    public String createFollow(FollowForm followForm) {
        Long memberId = followForm.getmemberId();
        Long targetMemberId = followForm.getTargetMemberId();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        Member targetMember = memberRepository.findById(targetMemberId).orElseThrow(() -> new IllegalArgumentException("대상 사용자를 찾을 수 없습니다"));

        if (memberId.equals(targetMemberId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(member, targetMember);
        if (existingFollow.isPresent()) {
            throw new IllegalStateException("이미 팔로우하고 있습니다.");
        }

        Follow follow = new Follow();
        follow.setFollower(member);
        follow.setFollowing(targetMember);
        followRepository.save(follow);
        return "사용자가 성공적으로 팔로우 목록에 추가되었습니다.";
    }

    // 서비스 계층에 정의된 getFollowingList 메소드
    public List<Follow> getFollowingList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        return followRepository.findByFollower(member);
    }

    public List<Follow> getFollowerList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        // 자기 자신을 제외한 팔로워 목록을 반환합니다.
        return followRepository.findByFollowing(member).stream()
                .filter(follow -> !follow.getFollower().getId().equals(memberId))
                .collect(Collectors.toList());
    }

    public String cancelFollow(Long memberId, Long targetMemberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Member targetMember = memberRepository.findById(targetMemberId).orElseThrow(() -> new IllegalArgumentException("Target member not found"));
        Follow follow = followRepository.findByFollowerAndFollowing(member, targetMember)
                .orElseThrow(() -> new IllegalStateException("Follow not found"));
        followRepository.delete(follow);
        return "Follow successfully cancelled";
    }
}
