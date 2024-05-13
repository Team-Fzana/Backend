package com.example.fzana.controller;

import com.example.fzana.dto.ScheduleRequest;
import com.example.fzana.dto.ScheduleResponse;
import com.example.fzana.exception.MemberNotFoundException;
import com.example.fzana.exception.ScheduleNotFoundException;
import com.example.fzana.service.FollowService;
import com.example.fzana.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private FollowService followService;

    // 1. schedule 모두 조회
    @GetMapping("/calendar/{memberId}")
    @Operation(summary = "사용자의 모든 일정 조회", description = "사용자의 모든 일정과 투두리스트를 조회합니다.")
    public ResponseEntity<?> allTodoLists(@PathVariable Long memberId) {
        try{
            List<ScheduleResponse> scheduleList = scheduleService.scheduleList(memberId);
            return ResponseEntity.status(HttpStatus.OK).body(scheduleList);
        } catch (MemberNotFoundException e){
            // 없는 MemberID인 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "없는 사용자 입니다."));
        }

    }

    // 2. schedule 추가
    @PostMapping("/member/{memberId}/schedule")
    @Operation(summary = "사용자 schedule 추가", description = "사용자가 schedule을 추가 합니다.")
    public ResponseEntity<?> createTodoList(@PathVariable Long memberId,
                                                          @RequestBody ScheduleRequest scheduleRequest){
        try{
            // 서비스에 위임
            ScheduleResponse createdSchedule = scheduleService.createSchedule(memberId, scheduleRequest);
            // 결과 응답
            return ResponseEntity.status(HttpStatus.OK).body(createdSchedule);
        } catch (MemberNotFoundException e){
            // 없는 MemberID인 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "없는 사용자 입니다."));
        }

    }

    // 3. schedule 수정
    @PutMapping("/schedule/{scheduleId}")
    @Operation(summary = "사용자의 schedule 수정", description = "사용자의 schedule을 수정 합니다.")
    public ResponseEntity<?> updateTodoList(@PathVariable Long scheduleId,
                                            @RequestBody ScheduleRequest scheduleRequest){
        try{
            // 서비스에 위임
            ScheduleResponse updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleRequest);
            // 결과 응답
            return ResponseEntity.status(HttpStatus.OK).body(updatedSchedule);
        } catch (ScheduleNotFoundException e){
            // 없는 ScheduleID인 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "없는 일정입니다."));
        }

    }

    // 4. schedule 삭제
    @DeleteMapping("/schedule/{scheduleId}")
    @Operation(summary = "사용자의 schedule 삭제", description = "사용자의 schedule을 삭제 합니다.")
    public ResponseEntity<?> deleteTodoList(@PathVariable Long scheduleId){
        try{
            // 서비스에 위임
            ScheduleResponse deletedSchedule = scheduleService.delete(scheduleId);
            // 결과 응답
            return ResponseEntity.status(HttpStatus.OK).body(deletedSchedule);
        } catch (ScheduleNotFoundException e){
            // 없는 ScheduleID인 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "없는 일정입니다."));
        }

    }
    // 5. 특정 날짜의 스케줄 조회
    @GetMapping("/calendar/{memberId}/{date}")
    @Operation(summary = "특정 날짜의 스케줄 조회", description = "특정 사용자의 특정 날짜의 스케줄을 조회합니다.")
    public ResponseEntity<?> getScheduleForDate(
            @PathVariable Long memberId,
            @PathVariable("date")
            @Parameter(description = "조회할 날짜(YYYY-MM-DD 형식)", example = "2024-05-12")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<ScheduleResponse> schedules = scheduleService.getScheduleForDate(memberId, date);
            return ResponseEntity.ok(schedules);
        } catch (MemberNotFoundException e) {
            // 사용자를 찾을 수 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "사용자를 찾을 수 없습니다."));
        }
    }





}


