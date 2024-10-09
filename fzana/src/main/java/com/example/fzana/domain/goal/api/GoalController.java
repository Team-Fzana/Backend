package com.example.fzana.domain.goal.api;

import com.example.fzana.domain.exception.MemberNotFoundException;
import com.example.fzana.domain.exception.ScheduleNotFoundException;
import com.example.fzana.domain.goal.dto.GoalRequest;
import com.example.fzana.domain.goal.dto.GoalResponse;
import com.example.fzana.domain.goal.application.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goals")
public class GoalController {
    private final GoalService goalService;

    //목표 조회
    @GetMapping("/{memberId}")
    @Operation(summary = "사용자의 모든 goal 조회", description = "사용자의 모든 goal을 조회합니다.")
    public ResponseEntity<List<GoalResponse>> allGoals(@PathVariable Long memberId ) {
        try {
            List<GoalResponse> goalList = goalService.goalList(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(goalList);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    //목표 추가
    @PostMapping("/{memberId}")
    @Operation(summary = "goal 추가", description = "사용자가 goal을 추가합니다.")
    public ResponseEntity<GoalResponse> createGoal(@PathVariable Long memberId, @RequestBody GoalRequest goalRequest) {
        try {
            GoalResponse createdGoal = goalService.createGoal(memberId,goalRequest);
            return ResponseEntity.status(HttpStatus.OK).body(createdGoal);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    //목표 수정
    @PutMapping("/{goalId}")
    @Operation(summary = "goal 수정", description = "사용자가 goal을 수정힙니다.")
    public ResponseEntity<GoalResponse> update(@PathVariable final Long goalId, @RequestBody final GoalRequest goalRequest) {
       try{
           GoalResponse updatedGoal= goalService.update(goalId,goalRequest);
           return ResponseEntity.status(HttpStatus.OK).body(updatedGoal);
       } catch (ScheduleNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }

    }



    //목표 삭제
    @DeleteMapping("/{goalId}")
    @Operation(summary = "goal 삭제", description = "사용자가 goal을 삭제합니다.")
    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId) {
        goalService.delete(goalId);
        return ResponseEntity.noContent().build();
    }
}

