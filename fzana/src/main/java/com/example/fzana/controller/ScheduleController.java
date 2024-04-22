package com.example.fzana.controller;

import com.example.fzana.dto.ScheduleRequest;
import com.example.fzana.dto.ScheduleResponse;
import com.example.fzana.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 1. schedule 모두 조회
    @GetMapping("/calendar/{memberId}")
    @Operation(summary = "사용자의 모든 일정 조회", description = "사용자의 모든 일정과 투두리스트를 조회합니다.")
    public ResponseEntity<List<ScheduleResponse>> allTodoLists(@PathVariable Long memberId){
        // 서비스에 위임
        List<ScheduleResponse> scheduleList = scheduleService.scheduleList(memberId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(scheduleList);
    }

    // 2. schedule 추가
    @PostMapping("/member/{memberId}/schedule")
    @Operation(summary = "사용자 schedule 추가", description = "사용자가 schedule을 추가 합니다.")
    public ResponseEntity<ScheduleResponse> createTodoList(@PathVariable Long memberId,
                                                          @RequestBody ScheduleRequest scheduleRequest){
        // 서비스에 위임
        ScheduleResponse createdSchedule = scheduleService.createSchedule(memberId, scheduleRequest);
        // 결과 응답
        return (createdSchedule != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdSchedule) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 3. schedule 수정
    @PutMapping("/schedule/{scheduleId}")
    @Operation(summary = "사용자의 schedule 수정", description = "사용자의 schedule을 수정 합니다.")
    public ResponseEntity<ScheduleResponse> updateTodoList(@PathVariable Long scheduleId,
                                                          @RequestBody ScheduleRequest scheduleRequest){
        // 서비스에 위임
        ScheduleResponse updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleRequest);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedSchedule);
    }

    // 4. schedule 삭제
    @DeleteMapping("/schedule/{scheduleId}")
    @Operation(summary = "사용자의 schedule 삭제", description = "사용자의 schedule을 삭제 합니다.")
    public ResponseEntity<ScheduleResponse> deleteTodoList(@PathVariable Long scheduleId){
        // 서비스에 위임
        ScheduleResponse deletedSchedule = scheduleService.delete(scheduleId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedSchedule);
    }

    // 5. schedule 진행 체크(변경) -> 생각해보니 진행 체크(완료, 진행 중, 미 진행)은 schedule 수정에서 할 수 있지 않을까
}
