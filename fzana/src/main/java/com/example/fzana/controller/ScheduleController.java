package com.example.fzana.controller;

import com.example.fzana.domain.Schedule;
import com.example.fzana.dto.ScheduleForm;
import com.example.fzana.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 1. todo-list 추가
    @PostMapping("/user/{userId}/todo-list")
    public ResponseEntity<ScheduleForm> createTodoList(@PathVariable Long userId,
                                                       @RequestBody ScheduleForm scheduleForm){
        ScheduleForm createdSchedule = scheduleService.createSchedule(userId, scheduleForm);

        return (createdSchedule != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdSchedule) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 2. todo-list 수정
    @PutMapping("/todo-list/{todolistId}")
    public ResponseEntity<ScheduleForm> updateTodoList(@PathVariable Long todolistId,
                                                   @RequestBody ScheduleForm scheduleForm){
        ScheduleForm updatedSchedule = scheduleService.updateSchedule(todolistId, scheduleForm);

        return ResponseEntity.status(HttpStatus.OK).body(updatedSchedule);
    }
    // 3. todo-list 삭제
    // 4. todo-list 진행 체크(변경)
    // 5. calendar(todo-lists) 조회


}
