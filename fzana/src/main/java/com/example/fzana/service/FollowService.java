package com.example.fzana.service;

import com.example.fzana.domain.Follow;
import com.example.fzana.domain.Member;
import com.example.fzana.domain.Schedule;
import com.example.fzana.dto.*;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.repository.FollowRepository;
import com.example.fzana.repository.MemberRepository;
import com.example.fzana.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private NotificationService notificationService;

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

        //팔로우 당한 회원에게 알림 전송
        notificationService.sendNotification(targetMemberId,"새로운 팔로워가 있습니다: "+memberId);

        return "사용자가 성공적으로 팔로우 목록에 추가되었습니다.";


    }

    // 팔로잉 목록 가져오기
    public List<FollowingResponse> getFollowingList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        List<Follow> bringFollwings = followRepository.findByFollower(member);

        List<FollowingResponse> dtos = new ArrayList<>();
        for (int i = 0; i < bringFollwings.size(); i++){ // 1. 조회한 사용자 엔티티 수 만큼 반복
            Follow f = bringFollwings.get(i);            // 2. 조회한 사용자 엔티티 하나씩 가져오기
            FollowingResponse dto = FollowingResponse.createMember(f); // 3. 엔티티를 DTO로 변환
            dtos.add(dto);                             // 4. 변환한 DTO를 dtos 리스트에 삽입
        }

        return dtos;
    }

    // 팔로워 목록 가져오기
    public List<FollowerResponse> getFollowerList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        List<Follow> bringFollwers = followRepository.findByFollowing(member);

        List<FollowerResponse> dtos = new ArrayList<>();
        for (int i = 0; i < bringFollwers.size(); i++){ // 1. 조회한 사용자 엔티티 수 만큼 반복
            Follow f = bringFollwers.get(i);            // 2. 조회한 사용자 엔티티 하나씩 가져오기
            FollowerResponse dto = FollowerResponse.createMember(f); // 3. 엔티티를 DTO로 변환
            dtos.add(dto);                             // 4. 변환한 DTO를 dtos 리스트에 삽입
        }

        return dtos;
    }

    // 팔로잉 삭제
    public String cancelFollow(Long memberId, Long targetMemberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        Member targetMember = memberRepository.findById(targetMemberId).orElseThrow(() -> new IllegalArgumentException("Target member not found"));
        Follow follow = followRepository.findByFollowerAndFollowing(member, targetMember)
                .orElseThrow(() -> new IllegalStateException("Follow not found"));
        followRepository.delete(follow);
        return "Follow successfully cancelled";
    }

    // 팔로워 삭제
    public String cancelFollwer(Long memberId, Long targetMemberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));
        Member targetMember = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found"));
        Follow follow = followRepository.findByFollowingAndFollower(member, targetMember)
                .orElseThrow(() -> new IllegalStateException("Follower not found"));
        followRepository.delete(follow);
        return "Follower successfully deleted";
    }

    // Method to fetch a friend's calendar
    public List<ScheduleResponse> getFriendCalendars(Long userId, Long friendId) throws IllegalAccessException, EntityNotFoundException {
        Member user = memberRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Member friend = memberRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("Friend not found"));

        Optional<Follow> followCheck = followRepository.findByFollowerAndFollowing(user, friend);
        if (followCheck.isEmpty()) {
            throw new IllegalAccessException("You do not have permission to access this friend's calendar");
        }

        List<Schedule> schedules = scheduleRepository.findByMemberId(friend.getId());
        return schedules.stream()
                .map(ScheduleResponse::createSchedule)
                .collect(Collectors.toList());
    }

    public boolean isFollowing(Long memberId, Long targetMemberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));
        Member targetMember = memberRepository.findById(targetMemberId).orElseThrow(() -> new IllegalArgumentException("대상 사용자를 찾을 수 없습니다"));

        return followRepository.existsByFollowerIdAndFollowingId(member.getId(), targetMember.getId());
    }

}
