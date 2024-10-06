package com.example.fzana.domain.schedule.api;

import com.example.fzana.domain.schedule.dto.ScheduleRequest;
import com.example.fzana.domain.schedule.dto.ScheduleResponse;
import com.example.fzana.domain.schedule.application.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 1. schedule 모두 조회
    @GetMapping("/{memberId}")
    @Operation(summary = "사용자의 모든 일정 조회", description = "사용자의 모든 일정과 투두리스트를 조회합니다.")
    public ResponseEntity<List<ScheduleResponse>> allTodoLists(@PathVariable Long memberId) {
            List<ScheduleResponse> scheduleList = scheduleService.scheduleList(memberId);
            return ResponseEntity.status(HttpStatus.CREATED).body(scheduleList);
    }

    // 2. schedule 추가
    @PostMapping("/{memberId}")
    @Operation(summary = "사용자 schedule 추가", description = "사용자가 schedule을 추가 합니다.")
    public ResponseEntity<ScheduleResponse> createTodoList(@PathVariable Long memberId,
                                                          @RequestBody ScheduleRequest scheduleRequest){
            ScheduleResponse createdSchedule = scheduleService.createSchedule(memberId, scheduleRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    // 3. schedule 수정
    @PutMapping("/{scheduleId}")
    @Operation(summary = "사용자의 schedule 수정", description = "사용자의 schedule을 수정 합니다.")
    public ResponseEntity<ScheduleResponse> updateTodoList(@PathVariable Long scheduleId,
                                            @RequestBody ScheduleRequest scheduleRequest){
            ScheduleResponse updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedSchedule);
    }

    // 4. schedule 삭제
    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "사용자의 schedule 삭제", description = "사용자의 schedule을 삭제 합니다.")
    public ResponseEntity<?> deleteTodoList(@PathVariable Long scheduleId){
            ScheduleResponse deletedSchedule = scheduleService.delete(scheduleId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedSchedule);
    }

    // 5. 특정 날짜의 스케줄 조회
    @GetMapping("/{memberId}/{date}")
    @Operation(summary = "특정 날짜의 스케줄 조회", description = "특정 사용자의 특정 날짜의 스케줄을 조회합니다.")
    public ResponseEntity<List<ScheduleResponse>> getScheduleForDate(
            @PathVariable Long memberId,
            @PathVariable("date")
            @Parameter(description = "조회할 날짜(YYYY-MM-DD 형식)", example = "2024-05-12")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
            List<ScheduleResponse> schedules = scheduleService.getScheduleForDate(memberId, date);
            return ResponseEntity.ok(schedules);
    }

}


