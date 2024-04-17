package com.example.fzana.controller;

import com.example.fzana.domain.Follow;
import com.example.fzana.dto.FollowForm;
import com.example.fzana.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/{userId}/following/{targetUserId}")
    public ResponseEntity<?> createFollow(@PathVariable Long userId, @PathVariable Long targetUserId) {
        FollowForm followForm = new FollowForm(userId, targetUserId);
        try {
            String message = followService.createFollow(followForm);
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<?> getFollowingList(@PathVariable Long userId) {
        try {
            List<Follow> followingList = followService.getFollowingList(userId);
            List<FollowForm.FollowingDTO> result = followingList.stream().map(follow -> new FollowForm.FollowingDTO(
                    follow.getFollowing().getId(),
                    follow.getFollowing().getNickName()  // User 클래스에 getNickName() 메서드가 존재한다고 가정
            )).collect(Collectors.toList());

            return ResponseEntity.ok().body(Map.of("status", "200", "message", "팔로잉 목록 전체 조회 성공", "friends", result));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", "사용자를 찾을 수 없거나 팔로잉 목록을 검색하는 도중 오류가 발생했습니다."));
        }
    }

}
