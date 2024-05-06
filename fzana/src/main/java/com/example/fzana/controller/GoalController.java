package com.example.fzana.controller;

import com.example.fzana.dto.GoalRequest;
import com.example.fzana.dto.GoalResponse;
import com.example.fzana.dto.ScheduleResponse;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.exception.ScheduleNotFoundException;
import com.example.fzana.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GoalController {
    private final GoalService goalService;

    //목표 조회
    @GetMapping("/goal/{memberId}")
    @Operation(summary = "사용자의 모든 goal 조회", description = "사용자의 모든 goal을 조회합니다.")
    public ResponseEntity<?> allGoals(@PathVariable Long memberId ) {
        try {
            List<GoalResponse> goalList = goalService.goalList(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(goalList);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","없는 사용자입니다."));
        }
    }


    //목표 추가
    @PostMapping("/member/{memberId}/goal")
    @Operation(summary = "goal 추가", description = "사용자가 goal을 추가합니다.")
    public ResponseEntity<?> createGoal(@PathVariable Long memberId, @RequestBody GoalRequest goalRequest) {
        try {
            GoalResponse createdGoal = goalService.createGoal(memberId,goalRequest);
            return ResponseEntity.status(HttpStatus.OK).body(createdGoal);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","없는 사용자입니다."));
        }

    }

    //목표 수정
    @PutMapping("/goal/{goalId}")
    @Operation(summary = "goal 수정", description = "사용자가 goal을 수정힙니다.")
    public ResponseEntity<?> update(@PathVariable final Long goalId, @RequestBody final GoalRequest goalRequest) {
       try{
           GoalResponse updatedGoal= goalService.update(goalId,goalRequest);
           return ResponseEntity.status(HttpStatus.OK).body(updatedGoal);
       } catch (ScheduleNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "없는 일정입니다."));
       }

    }



    //목표 삭제
    @DeleteMapping("/goal/{goalId}")
    @Operation(summary = "goal 삭제", description = "사용자가 goal을 삭제합니다.")
    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId) {
        goalService.delete(goalId);
        return ResponseEntity.noContent().build();
    }
}

