package com.example.fzana.controller;

import com.amazonaws.Response;
import com.example.fzana.domain.Follow;
import com.example.fzana.dto.FollowForm;
import com.example.fzana.dto.FollowResponse;
import com.example.fzana.dto.ScheduleResponse;
import com.example.fzana.service.FollowService;
import com.example.fzana.service.ScheduleService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/{memberId}/following/{targetMemberId}")
    @Operation(summary = "사용자 팔로우", description = "사용자 ID를 사용하여 다른 사용자를 팔로우합니다.")
    public ResponseEntity<FollowResponse.MessageResponse> createFollow(@PathVariable Long memberId, @PathVariable Long targetMemberId) {
        FollowForm followForm = new FollowForm(memberId, targetMemberId);
        try {
            String message = followService.createFollow(followForm);
            return ResponseEntity.ok(new FollowResponse.MessageResponse(message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new FollowResponse.MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{memberId}/following")
    @Operation(summary = "팔로잉 목록 조회", description = "사용자 ID를 사용하여 해당 사용자가 팔로잉하고 있는 모든 사용자의 목록을 조회합니다.")
    public ResponseEntity<?> getFollowingList(@PathVariable Long memberId) {
        try {
            List<Follow> followingList = followService.getFollowingList(memberId);
            List<Map<String, Object>> result = followingList.stream().map(follow -> {
                Map<String, Object> map = new HashMap<>();
                map.put("followingId", follow.getFollowing().getId());
                map.put("name", follow.getFollowing().getNickName()); // Member 클래스에 getNickName() 메서드가 있다고 가정
                return map;
            }).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("status", "200");
            response.put("message", "팔로잉 목록 조회 성공");
            response.put("friends", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", "사용자를 찾을 수 없거나 팔로잉 목록을 조회하는 도중 오류가 발생했습니다."));
        }
    }

    @GetMapping("/{memberId}/followers")
    @Operation(summary = "팔로워 목록 조회", description = "사용자 ID를 사용하여 해당 사용자를 팔로우하고 있는 모든 사용자의 목록을 조회합니다.")
    public ResponseEntity<?> getFollowerList(@PathVariable Long memberId) {
        try {
            // 팔로워 목록 조회
            List<Follow> followersList = followService.getFollowerList(memberId);
            // 조회한 목록을 Map으로 변환
            List<Map<String, Object>> result = followersList.stream().map(follow -> {
                Map<String, Object> map = new HashMap<>();
                map.put("followerId", follow.getFollower().getId());
                map.put("name", follow.getFollower().getNickName()); // Member 클래스에 getNickName() 메서드가 있다고 가정
                return map;
            }).collect(Collectors.toList());

            // 응답 생성 및 반환
            Map<String, Object> response = new HashMap<>();
            response.put("status", "200");
            response.put("message", "팔로워 목록 조회 성공");
            response.put("followers", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 에러 응답 반환
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }


    @DeleteMapping("/{memberId}/unfollow/{targetMemberId}")
    @Operation(summary = "사용자 언팔로우", description = "사용자 ID와 대상 사용자 ID를 사용하여 팔로우를 취소합니다.")
    public ResponseEntity<?> deleteFollow(@PathVariable Long memberId, @PathVariable Long targetMemberId) {
        try {
            String message = followService.cancelFollow(memberId, targetMemberId);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{memberId}/unfollwer/{targetMemberId}")
    @Operation(summary = "내 팔로워 삭제", description = "사용자 Id와 대상 사용자 ID를 사용하여 팔로워를 삭제합니다.")
    public ResponseEntity<?> deleteFollwer(@PathVariable Long memberId, @PathVariable Long targetMemberId){
        try {
            String message = followService.cancelFollwer(memberId, targetMemberId);
            return ResponseEntity.ok(Map.of("message", message));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/{memberId}/friends/{friendId}/calendars")
    @Operation(summary = "친구 캘린더 조회", description = "사용자가 특정 친구의 캘린더를 조회합니다.")
    public ResponseEntity<?> getFriendCalendars(@PathVariable Long memberId, @PathVariable Long friendId) {
        try {
            // 팔로잉 관계 확인
            if (!followService.isFollowing(memberId, friendId)) {
                throw new IllegalStateException("둘이 친구 관계가 아닙니다.");
            }

            List<ScheduleResponse> calendars = followService.getFriendCalendars(memberId, friendId);
            return ResponseEntity.ok(calendars);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // 오류 메시지를 담은 ScheduleResponse 객체를 생성하여 리스트에 추가
            ScheduleResponse errorResponse = new ScheduleResponse(e.getMessage());
            return ResponseEntity.status(404).body(Collections.singletonList(errorResponse));
        }
    }

    @GetMapping("/{memberId}/friends/{target_calendar_id}/{date}")
    @Operation(summary = "특정 날짜의 친구 캘린더 조회", description = "사용자가 특정 친구의 특정 날짜의 캘린더를 조회합니다.")
    public ResponseEntity<?> getFriendCalendarForDate(
            @PathVariable("memberId") Long memberId,
            @PathVariable("target_calendar_id") Long targetCalendarId,
            @PathVariable("date")
            @Parameter(description = "조회할 날짜(YYYY-MM-DD 형식)", example = "2024-05-12")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<ScheduleResponse> schedules = scheduleService.getFriendCalendarForDate(memberId, targetCalendarId, date);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error retrieving friend calendar for date: " + e.getMessage());
        }
    }
}