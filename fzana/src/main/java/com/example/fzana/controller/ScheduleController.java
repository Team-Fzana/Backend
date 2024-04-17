package com.example.fzana.controller;

import com.example.fzana.domain.Schedule;
import com.example.fzana.dto.ScheduleForm;
import com.example.fzana.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 1. todo-list, 일정 모두 조회
    @GetMapping("/calendar/{userId}")
    public ResponseEntity<List<ScheduleForm>> allTodoLists(@PathVariable Long userId){
        // 서비스에 위임
        List<ScheduleForm> scheduleList = scheduleService.scheduleList(userId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(scheduleList);
    }

    // 2. todo-list 추가
    @PostMapping("/user/{userId}/todo-list")
    public ResponseEntity<ScheduleForm> createTodoList(@PathVariable Long userId,
                                                       @RequestBody ScheduleForm scheduleForm){
        // 서비스에 위임
        ScheduleForm createdSchedule = scheduleService.createSchedule(userId, scheduleForm);
        // 결과 응답
        return (createdSchedule != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdSchedule) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 3. todo-list 수정
    @PutMapping("/todo-list/{todolistId}")
    public ResponseEntity<ScheduleForm> updateTodoList(@PathVariable Long todolistId,
                                                   @RequestBody ScheduleForm scheduleForm){
        // 서비스에 위임
        ScheduleForm updatedSchedule = scheduleService.updateSchedule(todolistId, scheduleForm);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedSchedule);
    }

    // 4. todo-list 삭제
    @DeleteMapping("/todo-list/{todolistId}")
    public ResponseEntity<ScheduleForm> deleteTodoList(@PathVariable Long todolistId){
        // 서비스에 위임
        ScheduleForm deletedSchedule = scheduleService.delete(todolistId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedSchedule);
    }

    // 5. todo-list 진행 체크(변경) -> 생각해보니 진행 체크(완료, 진행 중, 미 진행)은 todo-list 수정에서 할 수 있지 않을까? 라는 생각이 드네요

}
